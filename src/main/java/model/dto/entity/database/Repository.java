package model.dto.entity.database;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Repository<PK, T> {
  Optional<T> getByKey(PK key) throws Exception, Exception;

  T persist(T entity) throws Exception, Exception;

  int persistBatch(List<T> entity) throws Exception, Exception;

  T update(T entity) throws Exception, Exception;

  int updateBatch(List<T> entityList) throws Exception, Exception;

  void delete(T entity) throws Exception, Exception;

  FetchResult<T> search(String q, Map<String, Object> map, int max, int offset) throws Exception, Exception;

  @SuppressWarnings("unchecked")
  FetchResult<T> search(String q, Map<String, Object> map, String orderBy, int max, int offset) throws Exception,
                                                                                                       Exception;

  FetchResult<Object> searchAdvanced(
      String q,
      String fields,
      Map<String, Object> map,
      String orderBy,
      int max,
      int offset) throws Exception, Exception;

  boolean block(long id, boolean block) throws Exception, Exception;

  int update(String q, Map<String, Object> map) throws Exception, Exception;

  List<Object> findAllBy(String q, Map<String, Object> map) throws Exception;

  List<Object> findAllBy(String q, Map<String, Object> map, int max) throws Exception;

  Object findBy(String q, Map<String, Object> map) throws Exception;

  int executeNative(String q) throws Exception;
}