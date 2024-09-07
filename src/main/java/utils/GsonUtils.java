package utils;

import com.google.gson.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {

    private final static Gson CONVERTER = new Gson();
    ;

    public static <T> Document entityToMongoDocument(final T entity) {
        return Document.parse(CONVERTER.toJson(entity));
    }

    public static <T> T mongoDocumentToEntity(final Document document, final Class<T> entityClass) {
        System.out.println();
        return CONVERTER.fromJson(document.toJson(), entityClass);
    }

}
