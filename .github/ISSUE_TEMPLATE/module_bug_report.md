---
name: Звіт про дефект модуля (JUnit)
about: Дефект модуля бізнес-логіки, знайдений автотестом. Один дефект — один Issue
labels: ["status:new", "module", "sprint:4"]
---
**Severity:** Blocker | Critical | Major | Normal | Minor | Trivial
**Priority:** P1 | P2 | P3
**Linked:** requirement BL-__; test `<Клас>Test#<метод>` (рядок параметризованого тесту [n], якщо є)

### Description
Яке правило BL порушено і як це виявлено тестом.

### Steps to reproduce
1. `git checkout <коміт/тег>` (модуль v1)
2. `mvn test -Dtest=<Клас>Test#<метод>`
3. Виклик, що перевіряється: `<Клас>.<метод>(<вхідні дані>)`

### Actual result
`expected: <…> but was: <…>` (рядок зі звіту Surefire)

### Expected result
Значення, обчислене з вимоги BL-__ вручну: …

### Environment
finkit-module v1.0.0; JDK 17.x; Maven 3.9.x; ОС; команда `mvn test -Dtest=…`

### Attachments
Фрагмент `target/surefire-reports/<Клас>Test.txt`

**Reviewer:** ПІБ, дата
