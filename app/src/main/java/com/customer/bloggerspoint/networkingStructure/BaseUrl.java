package com.customer.bloggerspoint.networkingStructure;

public class BaseUrl {

    private String BASE_URL="http://worldtpm.dx.am";

    private String GET_BLOGS = BASE_URL+"/api/pocketNotes/getNotes.php?u=";

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getGET_BLOGS() {
        return GET_BLOGS;
    }
}
