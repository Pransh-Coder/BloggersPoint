package com.customer.bloggerspoint.networkingStructure;

import androidx.annotation.Nullable;

public interface NetworkingInterface {

    <T> void networkingRequestPerformed(@Nullable MethodType type, boolean status, @Nullable T error);

    enum MethodType {
        postBlog
    }
}
