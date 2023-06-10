# Hibernate Envers and Spring Data Envers

- Has an integration with Spring Data as a revision repository -> Spring Data Envers
    - It exposes some basic querying methods like findLatestRevision, findRevision, findAllRevisions etc.
    - Query by date: does not provide this out-of-the-box but you can fetch all revisions and do the logic at a higher
      level
- You can use it without spring data
    - By using the EntityManager and AuditQuery to perform the queries
    - Query by date: you can construct the query to do so
- Saves for each audited entity an extra table with the same name but with _audit at the end
- Revisions are universal per db and not unique per table
    - You can query multiple entity types by the same revision
    - Basically the framework assigns one revision per transaction and each entity this transaction has edited will get
      a new row in its corresponding audit table with the same revision id for all
- One table is created for revision info
    - Only one per db
    - It contains the timestamp
- Revisions have types INSERT, UPDATE & DELETE
    - Deletes are registered as revisions
    - Editing after deletion is allowed and if you want to deny it you have to do so at a higher level
- Does not work with nosql dbs