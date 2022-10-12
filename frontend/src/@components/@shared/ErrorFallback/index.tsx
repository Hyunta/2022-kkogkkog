import { AxiosError } from 'axios';

import landingLogoImage from '@/assets/images/landing_logo.png';
import theme from '@/styles/theme';

import Icon from '../Icon';
import PageTemplate from '../PageTemplate';
import * as Styled from './style';

export interface ErrorFallbackProps {
  error: AxiosError;
  resetErrorBoundary?: () => void;
}

const ErrorFallback = ({ error, resetErrorBoundary }: ErrorFallbackProps) => {
  return (
    <PageTemplate.ExtendedStyleHeader title='문제가 발생했어요'>
      <Styled.Root>
        <img src={landingLogoImage} alt='로고' width='86' />
        <Styled.ResetSection onClick={resetErrorBoundary}>
          <Icon iconName='reload' color={theme.colors.light_grey_200} />
          <button>다시 불러오기</button>
        </Styled.ResetSection>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default ErrorFallback;
