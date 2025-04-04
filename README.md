# 📦 Github API Service

Projekt oparty na Spring Boot, który łączy się z [GitHub REST API](https://docs.github.com/en/rest), aby pobierać publiczne repozytoria użytkownika, filtrować te, które **nie są forkami**, i zwracać ich listę wraz z gałęziami.

## 🚀 Funkcjonalności

- 🔍 Pobieranie publicznych repozytoriów użytkownika GitHub
- 🧹 Filtrowanie tylko **nie-forkowanych** repozytoriów
- 🌿 Pobieranie listy gałęzi dla każdego repozytorium
- ❗ Obsługa błędów (np. użytkownik nie istnieje)

## 📌 Przykład użycia

```java
@Autowired
private GithubService githubService;

List<GithubRepository> repos = githubService.getNonForkRepositories("octocat");

Zwraca listę repozytoriów użytkownika octocat, które nie są forkami, wraz z gałęziami każdego z nich.
