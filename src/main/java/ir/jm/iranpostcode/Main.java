package ir.jm.iranpostcode;

import ir.jm.iranpostcode.model.dto.entity.AutocompleteRes;
import ir.jm.iranpostcode.model.dto.entity.AvensRes;
import ir.jm.iranpostcode.model.dto.entity.BuildsRes;
import ir.jm.iranpostcode.model.dto.entity.Data;
import ir.jm.iranpostcode.model.dto.entity.Datum;
import ir.jm.iranpostcode.model.dto.entity.PostCodeRes;
import ir.jm.iranpostcode.model.dto.entity.Query;
import ir.jm.iranpostcode.model.dto.entity.Suggestion;
import ir.jm.iranpostcode.model.dto.entity.database.RepositoryImpAbstract;
import ir.jm.iranpostcode.model.dto.entity.database.RepositoryManager;
import service.web.Services;

public class Main {
  final static String query = "خلیج";


  public static void main(String[] args) throws Exception {
    Services services = new Services();
    for (int i = 1; i <= 10000; i++) {
      final RepositoryImpAbstract queryRepository = RepositoryManager.getByEntity(Query.class);
      final Query entity = new Query();
      entity.setHouseNum(i);
      entity.setQuery(query);
    /*  final Optional byKey = queryRepository.getByKey(entity);
      if (byKey.isPresent()) {
        continue;
      }*/
      try {                                         
        queryRepository.persist(entity);
      } catch (Exception e) {

      }

      final AutocompleteRes autoComplete = services.getAutoComplete(i, query);
      try {
        queryRepository.persist(autoComplete);
      } catch (Exception e) {
        continue;
      }

      for (Suggestion suggestion : autoComplete.getSuggestions()) {
        final AvensRes builds = services.getAvens(suggestion);
        try {
          queryRepository.persist(builds);
        } catch (Exception e) {
          continue;
        }
        for (Datum datum : builds.getData()) {
          final BuildsRes buildsRes = services.getBuilds(datum, suggestion);
          try {
            queryRepository.persist(buildsRes);
          } catch (Exception e) {
            continue;
          }
          for (Data data : buildsRes.getData()) {
            final PostCodeRes postCode = services.getPostCode(data);
            try {
              queryRepository.persist(postCode);
            } catch (Exception e) {
              continue;
            }
            System.out.println(postCode.toString());
            if (
                postCode.getPostcode().contains("1378678977")
                || postCode.getPostcode().contains("1378944531")
                ) {
              System.err.println("FINDDDDDDDDDDDDDDDDDDDDDDDD!!!!!!!!!");
              System.exit(1);
            }
          }

        }
      }

    }
  }


}
