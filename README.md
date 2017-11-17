spec-demo
=========

Spring Boot web app exposing simple `PersonController`, which supports following scenarios:
- creating new person resource
- getting specific person by its id
- listing person resources filtered by name or surname.

The main goal was to present basic use cases of two libraries:
- [specification-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver) (used to filter person list)
- _Spring HATEOAS_ (used to automatically build links to resources)

Application leverages _H2 Database Engine_ so it is ready to download and play with.