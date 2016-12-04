package com.neko.selectgithubuser.bean;

import com.google.gson.annotations.SerializedName;
import com.neko.selectgithubuser.constants.ProtocolKey;

import java.util.List;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class GithubRepos {
    private List<GithubUserRepos> mGithubUserReposes;

    public List<GithubUserRepos> getGithubUserReposes() {
        return mGithubUserReposes;
    }

    public void setGithubUserReposes(List<GithubUserRepos> githubUserReposes) {
        mGithubUserReposes = githubUserReposes;
    }

   public static class GithubUserRepos {
        @SerializedName(ProtocolKey.Language)
        private String mLanguage;

        public String getLanguage() {
            return mLanguage;
        }

        public void setLanguage(String language) {
            mLanguage = language;
        }
    }
}
