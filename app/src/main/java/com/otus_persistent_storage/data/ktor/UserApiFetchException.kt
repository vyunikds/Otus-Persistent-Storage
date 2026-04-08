package com.otus_persistent_storage.data.ktor

class UserApiFetchException(cause: Throwable? = null) :
    Exception("Failed to fetch users from API", cause)