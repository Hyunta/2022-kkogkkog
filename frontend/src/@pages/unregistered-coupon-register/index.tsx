import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useFetchUnregisteredCouponByCode } from '@/@hooks/@queries/unregistered-coupon';

import NotFoundPage from '../404';
import ExpiredUnregisteredCouponPage from './ExpiredRegisteredCouponPage';
import IssuedUnregisteredCouponPage from './IssuedRegisteredCouponPage';
import RegisteredCouponPage from './RegisteredRegisteredCouponPage';

const UnregisteredCouponCodeProxyPage = () => {
  const couponCode = useGetSearchParam('couponCode') ?? '';

  const { unregisteredCoupon } = useFetchUnregisteredCouponByCode(couponCode);

  if (!unregisteredCoupon) {
    return <NotFoundPage />;
  }

  return (
    <>
      {unregisteredCoupon.unregisteredCouponStatus === 'ISSUED' && (
        <IssuedUnregisteredCouponPage
          unregisteredCoupon={unregisteredCoupon}
          couponCode={couponCode}
        />
      )}
      {unregisteredCoupon.unregisteredCouponStatus === 'REGISTERED' && <RegisteredCouponPage />}
      {unregisteredCoupon.unregisteredCouponStatus === 'EXPIRED' && (
        <ExpiredUnregisteredCouponPage />
      )}
    </>
  );
};

export default UnregisteredCouponCodeProxyPage;
