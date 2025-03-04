package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class WoowacoursePushAlarmEvent {

    private final String hostMemberUserId;
    private final String message;
    private final CouponEventType couponEvent;

    private WoowacoursePushAlarmEvent(String hostMemberUserId, String message, CouponEventType couponEvent) {
        this.hostMemberUserId = hostMemberUserId;
        this.message = message;
        this.couponEvent = couponEvent;
    }

    public static WoowacoursePushAlarmEvent of(String userId, CouponHistory couponHistory) {
        return new WoowacoursePushAlarmEvent(userId, couponHistory.toNoticeMessage(),
            couponHistory.getCouponEventType());
    }

    public boolean shouldNotSendPushAlarm() {
        return couponEvent == CouponEventType.FINISH;
    }
}
