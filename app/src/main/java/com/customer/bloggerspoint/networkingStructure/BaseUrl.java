package com.customer.bloggerspoint.networkingStructure;

public class BaseUrl {

    private String BASE_URL="http://paytmpay001.dx.am/blogers_point/";

    private String GET_BLOGS = BASE_URL+"getBlogs.php";     //getBlogs.php      api/pocketNotes/getNotes.php?u=

    private String SIGNUP = BASE_URL+"api/signup.php";

    private String LOGIN = BASE_URL+"api/login.php";

    private String ADD_BLOG = BASE_URL+"api/uploadBlog.php";

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getGET_BLOGS() {
        return GET_BLOGS;
    }

    public String getSIGNUP() {
        return SIGNUP;
    }

    public String getLOGIN() {
        return LOGIN;
    }

    public String getADD_BLOG() {
        return ADD_BLOG;
    }
}
