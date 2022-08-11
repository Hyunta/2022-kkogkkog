package com.woowacourse.kkogkkog.coupon.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponReservationData;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponReservationResponse {

    private Long couponId;
    private Long reservationId;
    private Long memberId;
    private String nickname;
    private String hashtag;
    private String description;
    private String couponType;
    private String couponStatus;
    private String message;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime meetingDate;


    public CouponReservationResponse(Long couponId,
                                     Long reservationId,
                                     Long memberId,
                                     String nickname,
                                     String hashtag,
                                     String description,
                                     CouponType couponType,
                                     CouponStatus couponStatus,
                                     String message,
                                     LocalDateTime meetingDate) {
        this.couponId = couponId;
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType.name();
        this.couponStatus = couponStatus.name();
        this.message = message;
        this.meetingDate = meetingDate;
    }

    public static CouponReservationResponse of(CouponReservationData data) {
        return new CouponReservationResponse(
            data.getCouponId(), data.getReservationId(), data.getMemberId(), data.getNickname(),
            data.getHashtag(), data.getDescription(),
            data.getCouponType(), data.getCouponStatus(),
            data.getMessage(), data.getMeetingDate());
    }
}
