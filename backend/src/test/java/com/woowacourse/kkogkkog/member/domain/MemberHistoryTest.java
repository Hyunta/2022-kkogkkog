//package com.woowacourse.kkogkkog.member.domain;
//
//import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
//import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponEvent;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//class MemberHistoryTest {
//
//    @Nested
//    @DisplayName("toNoticeMessage 메서드는")
//    class ToNoticeMessage {
//
//        @Test
//        @DisplayName("기록의 상대방 회원이 발생시킨 쿠폰 이벤트를 토대로 알림 메시지를 만들어 반환한다.")
//        void generateString() {
//            Workspace workspace = KKOGKKOG.getWorkspace(1L);
//            Member jeong = JEONG.getMember(workspace);
//            Member leo = LEO.getMember(workspace);
//            CouponType coffee = CouponType.COFFEE;
//            Coupon coupon = COFFEE.getCoupon(jeong, leo);
//
//            MemberHistory memberHistory = new MemberHistory(null, leo, jeong, coupon.getId(),
//                coupon.getCouponType(), new CouponEvent(CouponEventType.INIT, null), null, "메세지");
//
//            String actual = memberHistory.toNoticeMessage();
//            String expected = String.format("`%s` 님이 `%s` 쿠폰을 *보냈어요*\uD83D\uDC4B", jeong.getNickname(), coffee.getDisplayName());
//
//            assertThat(actual).isEqualTo(expected);
//        }
//    }
//
//    @Test
//    @DisplayName("단일 알림에 대해서 읽지 않았을 경우, 읽음으로 상태를 변경한다")
//    void isRead() {
//        // given
//        Workspace workspace = KKOGKKOG.getWorkspace(1L);
//        Member sender = SENDER.getMember(workspace);
//        Member receiver = RECEIVER.getMember(workspace);
//        MemberHistory memberHistory = new MemberHistory(
//            1L, sender, receiver, 1L, CouponType.valueOf("COFFEE"), new CouponEvent(CouponEventType.INIT, null), LocalDateTime.now(), "메시지");
//
//        // when
//        memberHistory.updateIsRead();
//
//        // then
//        assertThat(memberHistory.getIsRead()).isTrue();
//    }
//}
