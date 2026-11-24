# qa-finkit — автотести продукту «ФінКит»

**Дисципліна:** Технології забезпечення якості програмних засобів · **Команда:** Група F · **Продукт:** П2 «ФінКит» (`finkit`)
**Модуль:** `finkit-module` v1.0.0 (бізнес-логіка, вимоги BL-10…BL-80) · **Demo:** [ParaBank](https://parabank.parasoft.com/parabank/index.htm)
**Трекер дефектів:** [GitHub Issues](https://github.com/0ffmama/FinKit/issues) (дефекти модуля — мітка `module`)

## Команда
| Учасник | GitHub | Роль у Спринті 4 |
| :--- | :--- | :--- |
| Фомін Т. В. | `@0ffmama` | Інженер з тестування (ІТ) |

## 1. Вимоги до робочого місця
| Що | Версія | Перевірка |
| :--- | :--- | :--- |
| JDK | 17 або новіша (збірка йде з `release 17`) | `java -version` |
| Maven | 3.9+ | `mvn -v` |
| Chrome або Chromium | актуальна; драйвер завантажує Selenium Manager | — |
| Інтернет | перший запуск (залежності Maven) і UI-тести | — |

## 2. Збирання і запуск
```bash
git clone https://github.com/0ffmama/FinKit.git && cd FinKit
mvn clean test                                   # юніт-тести (UI-тести з тегом ui виключено)
mvn test -DexcludedGroups=none                   # юніт + UI (headless)
mvn test -Dtest=AccountLedgerTest                # один клас
mvn test -DexcludedGroups=none -Dtest=SmokeUiTest -Dheaded=true   # UI з видимим браузером
```
`BUILD FAILURE` після `mvn clean test` очікуваний: його дають 10 червоних тестів, що фіксують дефекти модуля v1 (розділ 5). Звіти: `target/surefire-reports/` (`*.txt` — підсумок, `TEST-*.xml` — усі тести). Протокол запуску — [docs/sprint4/surefire-summary.md](docs/sprint4/surefire-summary.md).

## 3. Структура
```
pom.xml                                   JDK 17, JUnit 5.10, Selenium 4.21, Surefire 3.2
src/main/java/ua/kpi/tzyapz/finkit/       модуль v1: LoanCalculator, TransferValidator, AccountLedger (+ TransferError, LedgerEntry)
src/test/java/ua/kpi/tzyapz/finkit/       юніт-тести: по класу на клас модуля
src/test/java/ua/kpi/tzyapz/finkit/ui/    WebDriver-тести (тег ui)
selenium-ide/                             проєкт Selenium IDE (*.side) і експорт у Java (export/, не збирається)
docs/sprint1 … sprint4/                   досьє якості за спринтами
```

## 4. Тести та автори
| Тест-клас | Вимоги | Тестів | Автор | Рецензент |
| :--- | :--- | :--- | :--- | :--- |

## 5. Дефекти модуля v1
| Bug ID | Вимога | Тест | Severity |
| :--- | :--- | :--- | :--- |

## 6. Документи
[Статут](docs/team/charter.md) · [План С4](docs/sprint4/plan.md) · [Матриця покриття BL](docs/sprint4/coverage-matrix.md) · [UI-сценарії](docs/sprint4/ui-scenarios.md) · [Таблиця задач С4](docs/sprint4/task-board.xlsx) · [Досьє v4 «Автоматизація»](docs/sprint4/README.md) · [Code review](docs/sprint4/code-review.md)
