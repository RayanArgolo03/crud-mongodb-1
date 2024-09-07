package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {

    private final static Gson CONVERTER = new Gson();

    public static <T> Document entityToDocument(final T entity) {
        return Document.parse(CONVERTER.toJson(entity));
    }

    public static <T> T documentToEntity(final Document document, final Class<T> entityClass) {
        return CONVERTER.fromJson(document.toJson(), entityClass);
    }

}
