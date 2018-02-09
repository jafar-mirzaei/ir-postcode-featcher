package model.dto.entity.database;



import model.dto.entity.AutocompleteRes;
import model.dto.entity.AvensRes;
import model.dto.entity.BuildsRes;
import model.dto.entity.Data;
import model.dto.entity.Datum;
import model.dto.entity.PostCodeRes;
import model.dto.entity.Query;
import model.dto.entity.Suggestion;
import model.dto.entity.impl.AutocompleteResRepositoryImpl;
import model.dto.entity.impl.AvensResRepositoryImpl;
import model.dto.entity.impl.BuildsResRepositoryImpl;
import model.dto.entity.impl.DataRepositoryImpl;
import model.dto.entity.impl.DatumRepositoryImpl;
import model.dto.entity.impl.PostCodeResRepositoryImpl;
import model.dto.entity.impl.QueryRepositoryImpl;
import model.dto.entity.impl.SuggestionRepositoryImpl;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum RepositoryManager {
    INSTANCE;
    private final EntityManagerFactory emFactory;

    RepositoryManager() {
        //persistence-unit
        this.emFactory = Persistence.createEntityManagerFactory("tets-post");
    }

    public EntityManager getEntityManager() {
        return this.emFactory.createEntityManager();
    }

    public void close() {
        this.emFactory.close();
    }

    //----------------------------------------------------------------------------------------------

    private static final Map<Class, RepositoryImpAbstract> ENTITY_REPO_MAP = new HashMap<>();

    static {
        ENTITY_REPO_MAP.put(AutocompleteRes.class, new AutocompleteResRepositoryImpl());
        ENTITY_REPO_MAP.put(AvensRes.class, new AvensResRepositoryImpl());
        ENTITY_REPO_MAP.put(BuildsRes.class, new BuildsResRepositoryImpl());
        ENTITY_REPO_MAP.put(Data.class, new DataRepositoryImpl());
        ENTITY_REPO_MAP.put(Datum.class, new DatumRepositoryImpl());
        ENTITY_REPO_MAP.put(PostCodeRes.class, new PostCodeResRepositoryImpl());
        ENTITY_REPO_MAP.put(Query.class, new QueryRepositoryImpl());
        ENTITY_REPO_MAP.put(Suggestion.class, new SuggestionRepositoryImpl());
    }

    public static RepositoryImpAbstract getByEntity(Class clazz) {
        return ENTITY_REPO_MAP.get(clazz);
    }
}
