import { lazy, Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Loading from '@/@components/@shared/Loading';
import OnlyNumberDynamicRouting from '@/@components/@shared/OnlyNumberDynamicRouting';
import UnregisteredCouponCreate from '@/@pages/unregistered-coupon-list/create';

import ErrorBoundaryWithHooks from './@components/@shared/ErrorBoundary';
import { useFetchMe } from './@hooks/@queries/user';

const NotFoundPage = lazy(() => import('@/@pages/404'));
const CouponListPage = lazy(() => import('@/@pages/coupon-list'));
const CouponCreatePage = lazy(() => import('@/@pages/coupon-list/create'));
const UnRegisteredCouponList = lazy(() => import('@/@pages/unregistered-coupon-list'));
const UnregisteredCouponDetail = lazy(() => import('@/@pages/unregistered-coupon-detail'));
const UnregisteredCouponCodeProxyPage = lazy(() => import('@/@pages/unregistered-coupon-register'));
const UserHistoryPage = lazy(() => import('@/@pages/history'));
const JoinPage = lazy(() => import('@/@pages/join'));
const LandingPage = lazy(() => import('@/@pages/landing'));
const MainPage = lazy(() => import('@/@pages/main'));
const ProfilePage = lazy(() => import('@/@pages/profile'));
const CouponDetailPage = lazy(() => import('@/@pages/coupon-list/coupon-detail'));
const CouponAcceptPage = lazy(() => import('@/@pages/coupon-list/coupon-detail/accept'));
const CouponDeclinePage = lazy(() => import('@/@pages/coupon-list/coupon-detail/decline'));
const CouponRequestPage = lazy(() => import('@/@pages/coupon-list/coupon-detail/request'));
const DownloadPage = lazy(() => import('@/@pages/download'));
const LoginPage = lazy(() => import('@/@pages/profile/login'));
const ProfileEditPage = lazy(() => import('@/@pages/profile/edit'));
const OAuthRedirect = lazy(() => import('@/@pages/oauth-redirect'));
const SlackDownloadRedirect = lazy(() => import('@/@pages/slack-download-redirect'));
const CouponCreateSelectPage = lazy(() => import('@/@pages/coupon-create-select'));

export const PATH = {
  MAIN: '/',
  LANDING: '/landing',
  COUPON_LIST: '/coupon-list',
  SENT_COUPON_LIST: '/coupon-list/sent',
  RECEIVED_COUPON_LIST: '/coupon-list/received',
  COUPON_CREATE: '/coupon-list/create',
  UNREGISTERED_COUPON_CREATE: '/unregistered-coupon-list/create',
  UNREGISTERED_COUPON_LIST: '/unregistered-coupon-list',
  UNREGISTERED_COUPON_DETAIL: '/unregistered-coupon-list/:unregisteredCouponId',
  UNREGISTERED_COUPON_REGISTER: '/unregistered-coupon-list/register',
  COUPON_CREATE_SELECT: '/coupon-create-select',
  LOGIN: '/login',
  SLACK_LOGIN_REDIRECT: '/login/redirect',
  GOOGLE_LOGIN_REDIRECT: '/login/google/redirect',
  SLACK_DOWNLOAD_REDIRECT: '/download/redirect',
  SIGNUP: '/signup',
  PROFILE: '/profile',
  PROFILE_EDIT: '/profile/edit',
  NOT_FOUND: '/*',
  USER_HISTORY: '/history',
  DOWNLOAD: '/download',
  COUPON_DETAIL: '/coupon-list/:couponId',
  COUPON_REQUEST: '/coupon-list/:couponId/request',
  COUPON_ACCEPT: '/coupon-list/:couponId/accept',
  COUPON_DECLINE: '/coupon-list/:couponId/decline',
  ERROR: '/error',
};

export const DYNAMIC_PATH = {
  COUPON_DETAIL(id: number | string): string {
    return `${PATH.COUPON_LIST}/${id}`;
  },
  COUPON_REQUEST(id: number | string): string {
    return `${PATH.COUPON_LIST}/${id}/request`;
  },
  COUPON_ACCEPT(id: number | string): string {
    return `${PATH.COUPON_LIST}/${id}/accept`;
  },
  COUPON_DECLINE(id: number | string): string {
    return `${PATH.COUPON_LIST}/${id}/decline`;
  },
  UNREGISTERED_COUPON_DETAIL(id: number | string): string {
    return `${PATH.UNREGISTERED_COUPON_LIST}/${id}`;
  },
  UNREGISTERED_COUPON_REGISTER(couponCode: string): string {
    return `${PATH.UNREGISTERED_COUPON_REGISTER}?couponCode=${couponCode}`;
  },
};

const Router = () => {
  return (
    <Suspense fallback={<Loading />}>
      <Routes>
        <Route path={PATH.LANDING} element={<LandingPage />} />
        <Route path={PATH.UNREGISTERED_COUPON_DETAIL} element={<UnregisteredCouponDetail />} />
        <Route
          path={PATH.UNREGISTERED_COUPON_REGISTER}
          element={
            // get 실패 이후에, reset 기능이 필요없는 fallback 에러가 필요함.
            <ErrorBoundaryWithHooks fallback={NotFoundPage}>
              <UnregisteredCouponCodeProxyPage />
            </ErrorBoundaryWithHooks>
          }
        />
        <Route element={<PublicRoute />}>
          <Route path={PATH.LOGIN} element={<LoginPage />} />
          <Route path={PATH.SIGNUP} element={<JoinPage />} />
          <Route path={PATH.SLACK_LOGIN_REDIRECT} element={<OAuthRedirect oAuthType='slack' />} />
          <Route path={PATH.GOOGLE_LOGIN_REDIRECT} element={<OAuthRedirect oAuthType='google' />} />
          <Route path={PATH.SLACK_DOWNLOAD_REDIRECT} element={<SlackDownloadRedirect />} />
          <Route path={PATH.DOWNLOAD} element={<DownloadPage />} />
        </Route>
        <Route element={<PrivateRoute />}>
          <Route path={PATH.MAIN} element={<MainPage />} />
          <Route path={PATH.SENT_COUPON_LIST} element={<CouponListPage />} />
          <Route path={PATH.RECEIVED_COUPON_LIST} element={<CouponListPage />} />
          <Route path={PATH.COUPON_CREATE_SELECT} element={<CouponCreateSelectPage />} />
          <Route path={PATH.COUPON_CREATE} element={<CouponCreatePage />} />
          <Route path={PATH.UNREGISTERED_COUPON_CREATE} element={<UnregisteredCouponCreate />} />
          <Route path={PATH.UNREGISTERED_COUPON_LIST} element={<UnRegisteredCouponList />} />
          {/* @TODO: Skeleton */}
          <Route
            path={PATH.COUPON_DETAIL}
            element={
              <OnlyNumberDynamicRouting>
                <CouponDetailPage />
              </OnlyNumberDynamicRouting>
            }
          />
          <Route
            path={PATH.COUPON_REQUEST}
            element={
              <OnlyNumberDynamicRouting>
                <CouponRequestPage />
              </OnlyNumberDynamicRouting>
            }
          />
          <Route
            path={PATH.COUPON_ACCEPT}
            element={
              <OnlyNumberDynamicRouting>
                <CouponAcceptPage />
              </OnlyNumberDynamicRouting>
            }
          />
          <Route
            path={PATH.COUPON_DECLINE}
            element={
              <OnlyNumberDynamicRouting>
                <CouponDeclinePage />
              </OnlyNumberDynamicRouting>
            }
          />
          <Route path={PATH.PROFILE} element={<ProfilePage />} />
          <Route path={PATH.PROFILE_EDIT} element={<ProfileEditPage />} />
          <Route path={PATH.USER_HISTORY} element={<UserHistoryPage />} />
        </Route>
        <Route path={PATH.NOT_FOUND} element={<NotFoundPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;

const PrivateRoute = () => {
  const { me, isLoading } = useFetchMe();

  return me ? (
    <Outlet />
  ) : (
    <CustomSuspense isLoading={isLoading} fallback={<Loading />}>
      <Navigate to={PATH.LOGIN} replace />
    </CustomSuspense>
  );
};

const PublicRoute = () => {
  const { me, isLoading } = useFetchMe();

  return me ? (
    <CustomSuspense isLoading={isLoading} fallback={<Loading />}>
      <Navigate to={PATH.MAIN} replace />
    </CustomSuspense>
  ) : (
    <Outlet />
  );
};
