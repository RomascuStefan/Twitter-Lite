package com.fiipractic.twitter.service;

import java.util.List;
import java.util.Map;

public interface FollowingService {

    List<Map<String, Object>> getMainPagePostWithID(String user_id);
    List<Map<String, Object>> getMainPagePostWithEmail(String email);


}
