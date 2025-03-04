//package com.woowacourse.kkogkkog.member.domain.repository;
//
//import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
//import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
//import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponEvent;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
//import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
//
//import com.woowacourse.kkogkkog.member.domain.Member;
//import com.woowacourse.kkogkkog.member.domain.MemberHistory;
//import com.woowacourse.kkogkkog.member.domain.Workspace;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@RepositoryTest
//class MemberHistoryRepositoryTest {
//
//    @Autowired
//    private MemberRepository members;
//
//    @Autowired
//    private WorkspaceRepository workspaces;
//
//    @Autowired
//    private MemberHistoryRepository memberHistories;
//
//    @Test
//    @DisplayName("countByHostMemberAndIsReadFalse 메서드는 기록의 주인이 읽지 않은 기록들의 개수를 반환한다.")
//    void countByHostMemberAndIsReadFalse() {
//        Workspace workspace = workspaces.save(KKOGKKOG.getWorkspace());
//        Member sender = members.save(JEONG.getMember(workspace));
//        Member receiver = members.save(LEO.getMember(workspace));
//        Coupon coupon = COFFEE.getCoupon(sender, receiver);
//
//        MemberHistory initHistory = toMemberHistory(receiver, sender, coupon, new CouponEvent(
//            CouponEventType.INIT, null));
//        initHistory.updateIsRead();
//        memberHistories.save(initHistory);
//        memberHistories.save(toMemberHistory(sender, receiver, coupon, new CouponEvent(
//            CouponEventType.REQUEST, null)));
//        memberHistories.save(toMemberHistory(receiver, sender, coupon, new CouponEvent(
//            CouponEventType.DECLINE, null)));
//        memberHistories.flush();
//
//        long actual = memberHistories.countByHostMemberAndIsReadFalse(receiver);
//        assertThat(actual).isEqualTo(1L);
//    }
//
//    private MemberHistory toMemberHistory(Member hostMember, Member targetMember, Coupon coupon,
//                                          CouponEvent event) {
//        return new MemberHistory(null, hostMember, targetMember, coupon.getId(),
//            coupon.getCouponType(), "메시지");
//    }
//}
