package io.terminus.dice.test.client.interceptor;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

@Slf4j
public class RetryInterceptor implements Interceptor {
    private int retryTimes;
    private long retryInterval;

    public RetryInterceptor(Builder builder) {
        this.retryInterval = builder.retryInterval;
        this.retryTimes = builder.retryTimes;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        boolean flag = true;
        Response response = null;
        Request request = chain.request();
        try {
            response = chain.proceed(request);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
            flag = false;
        }
        System.out.println(">>>flag=" + flag + "<<<");
        int retryNum = 0;
        while ((response == null || !flag) && retryNum <= this.retryTimes) {
            log.info(">>>retry " + (retryNum + 1) + " times, url=" + request.url());
            final long nextInterval = this.retryInterval;
            try {
                log.info("Wait for {}", nextInterval);
                Thread.sleep(nextInterval);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
            retryNum++;
            // retry the request
            try {
                response = chain.proceed(request);
                flag = true;
            } catch (Exception e) {
                log.error(Throwables.getStackTraceAsString(e));
                flag = false;
                if (retryNum >= retryTimes) {
                    throw e;
                }
            }
        }
        return response;
    }


    public static final class Builder {
        private int retryTimes;
        private long retryInterval;

        public Builder() {
            this.retryTimes = 5;
            this.retryInterval = 1000;
        }

        public RetryInterceptor.Builder retryTImes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public RetryInterceptor.Builder retryInterval(long retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public Interceptor build() {
            return new RetryInterceptor(this);
        }
    }
}
