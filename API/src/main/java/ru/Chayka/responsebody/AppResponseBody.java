package ru.Chayka.responsebody;

import lombok.Getter;
import lombok.Setter;
import ru.Chayka.responsebody.keys.ContentAppKey;

import java.util.List;

@Getter
@Setter
public final class AppResponseBody {
    private Integer status;
    private List<ContentAppKey> content;
}
