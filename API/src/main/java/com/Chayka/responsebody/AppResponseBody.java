package com.Chayka.responsebody;

import com.Chayka.commons.ResponseBody;
import com.Chayka.responsebody.keys.ContentAppKey;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class holds the information of the response body
 */
@Getter
@Setter
public final class AppResponseBody implements ResponseBody {
    private Integer status;
    private List<ContentAppKey> content;

    public Integer getStatusCode() {
        if (status == null) {
            return null;
        } else {
            return status;
        }
    }
}
