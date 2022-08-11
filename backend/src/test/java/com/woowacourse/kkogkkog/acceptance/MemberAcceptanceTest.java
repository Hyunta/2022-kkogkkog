package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입_또는_로그인에_성공한다;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.core.coupon.acceptance.CouponAcceptanceTest.쿠폰_생성을_요청하고;
import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.WORKSPACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberUpdateMeRequest;
import com.woowacourse.kkogkkog.presentation.dto.SuccessResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입된_전체_사용자_조회를_할_수_있다() {
        회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE.getMember()), WorkspaceResponse.of(WORKSPACE));
        회원가입_또는_로그인에_성공한다(MemberResponse.of(AUTHOR.getMember()), WorkspaceResponse.of(WORKSPACE));

        ExtractableResponse<Response> extract = 전체_사용자_조회를_요청한다();
        List<MemberResponse> members = extract.body().jsonPath()
            .getList("data", MemberResponse.class);
        SuccessResponse<List<MemberResponse>> membersResponse = new SuccessResponse<>(members);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(membersResponse.getData()).hasSize(2),
            () -> assertThat(membersResponse.getData()).usingRecursiveComparison()
                .ignoringFields("nickname").isEqualTo(List.of(
                        MemberResponse.of(new Member(1L, "rookieId1",
                            new Workspace(1L, "T03LX3C5540", "workspace_name", "ACCESS_TOKEN"),
                            "익명1234", "rookie@gmail.com", "https://slack")),
                        MemberResponse.of(new Member(2L, "authorId2",
                            new Workspace(1L, "T03LX3C5540", "workspace_name", "ACCESS_TOKEN"),
                            "익명4321", "author@gmail.com", "https://slack"))
                    )
                ));
    }

    @Test
    void 로그인_한_경우_본인의_정보를_조회할_수_있다() {
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE.getMember()),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();
        회원가입_또는_로그인에_성공한다(MemberResponse.of(AUTHOR.getMember()), WorkspaceResponse.of(WORKSPACE));

        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);
        MyProfileResponse memberResponse = extract.as(MyProfileResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberResponse).usingRecursiveComparison().ignoringFields("nickname")
                .isEqualTo(new MyProfileResponse(1L, "rookieId1", "T03LX3C5540", "workspace_name",
                    "익명1234", "rookie@gmail.com", "https://slack", 0L))
        );
    }

    @Test
    void 회원에게_전달된_알림들을_조회할_수_있다() {
        Member ROOKIE = new Member(1L, "URookie", null, "루키", "rookie@gmail.com", "image");
        Member ARTHUR = new Member(2L, "UArthur", null, "아서", "arthur@gmail.com", "image");
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();
        String arthurAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();
        쿠폰_생성을_요청하고(rookieAccessToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(arthurAccessToken)
            .get("/api/members/me/histories")
            .then().log().all()
            .extract();

        MemberHistoriesResponse memberHistoriesResponse = extract.as(MemberHistoriesResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberHistoriesResponse.getData()).hasSize(1)
        );
    }

    @Test
    void 조회하지_않은_알림이면_알림의_방문상태가_변경된다() {
        Member ROOKIE = new Member(1L, "URookie", null, "루키", "rookie@gmail.com", "image");
        Member ARTHUR = new Member(2L, "UArthur", null, "아서", "arthur@gmail.com", "image");
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();
        String arthurAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();
        쿠폰_생성을_요청하고(rookieAccessToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(arthurAccessToken)
            .patch("/api/members/me/histories/1")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 본인의_닉네임을_수정할_수_있다() {
        String newNickname = "새로운닉네임";
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE.getMember()),
            WorkspaceResponse.of(WORKSPACE)).getAccessToken();

        프로필_수정을_성공하고(newNickname, rookieAccessToken);
        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);
        String actual = extract.as(MyProfileResponse.class).getNickname();

        assertThat(actual).isEqualTo(newNickname);
    }

    private ExtractableResponse<Response> 본인_정보_조회를_요청한다(String accessToken) {
        return RestAssured.given().log().all()
            .when()
            .auth().oauth2(accessToken)
            .get("/api/members/me")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 전체_사용자_조회를_요청한다() {
        return RestAssured.given().log().all()
            .when()
            .get("/api/members")
            .then().log().all()
            .extract();
    }

    private void 프로필_수정을_성공하고(String nickname, String accessToken) {
        RestAssured.given().log().all()
            .when()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberUpdateMeRequest(nickname))
            .put("/api/members/me")
            .then().log().all()
            .extract();
    }
}
