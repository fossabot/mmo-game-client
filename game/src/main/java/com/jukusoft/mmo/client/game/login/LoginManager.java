package com.jukusoft.mmo.client.game.login;

import io.vertx.core.Handler;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginManager {

    protected static final LoginManager instance = new LoginManager();

    protected AtomicBoolean loggedIn = new AtomicBoolean(false);

    protected Handler<LoginRequest> loginExecutor = null;

    public enum LOGIN_RESPONSE {
        NO_SERVER, WRONG_CREDENTIALS, SUCCESSFUL
    }

    protected LoginManager () {
        //
    }

    /**
    * try to login user
    */
    public void login (String user, String password, Handler<LOGIN_RESPONSE> loginHandler) {
        if (this.loginExecutor == null) {
            throw new IllegalStateException("login executor wasnt set before.");
        }

        if (user == null) {
            throw new NullPointerException("user cannot be null.");
        }

        if (user.isEmpty()) {
            throw new IllegalArgumentException("user cannot be empty.");
        }

        if (password == null) {
            throw new NullPointerException("password cannot be null.");
        }

        if (password.isEmpty()) {
            throw new IllegalArgumentException("password cannot be empty.");
        }

        if (loginHandler == null) {
            throw new NullPointerException("login handler cannot be null.");
        }

        //execute login request
        LoginRequest req = new LoginRequest(user, password, loginHandler);
        this.loginExecutor.handle(req);
    }

    public boolean isLoggedIn () {
        return this.loggedIn.get();
    }

    public static LoginManager getInstance () {
        return instance;
    }

    public class LoginRequest {
        public final String user;
        public final String password;
        public final Handler<LOGIN_RESPONSE> loginHandler;

        public LoginRequest (String user, String password, Handler<LOGIN_RESPONSE> loginHandler) {
            this.user = user;
            this.password = password;
            this.loginHandler = loginHandler;
        }
    }

}
