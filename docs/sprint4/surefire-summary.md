# Протокол запуску автотестів — Спринт 4 (модуль v1)

**Дата:** 24.11.2026 · **Виконавець:** Фомін Т. В. (ІТ) · **Середовище:** Arch Linux, OpenJDK 21 (`release 17`), Maven 3.10, Chromium 156 (headless)
**Команда:** `mvn clean test -DexcludedGroups=none` · **Повні звіти:** [docs/sprint5/surefire-v1/](../sprint5/surefire-v1/) (база «до» для регресії Спринта 5)

## Підсумок
```
[ERROR] Tests run: 54, Failures: 10, Errors: 0, Skipped: 0
```
| Клас | Tests run | Failures | Примітка |
| :--- | :--- | :--- | :--- |
| LoanCalculatorTest | 17 | 5 | BUG-16, BUG-17 |
| TransferValidatorTest | 18 | 2 | BUG-18, BUG-19 |
| AccountLedgerTest | 11 | 3 | BUG-20, BUG-21, BUG-22 |
| ExampleTest (зразок пакета) | 2 | 0 | |
| ui.LoginTest, RegistrationTest, OpenAccountTest, TransferTest | 5 | 0 | UI-01…UI-05 |
| ui.SmokeUiTest | 1 | 0 | заготовка, поза нормативом |

`Tests run` рахує кожен рядок параметризованого тесту; тест-методів за нормативом — 20 юніт + 5 UI.
Лише юніт-тести (`mvn clean test`): `Tests run: 48, Failures: 10`.

## Тести, що падають (кожен = дефект модуля v1)
| Тест | Очікувано (з вимоги) | Фактично | Дефект |
| :--- | :--- | :--- | :--- |
| `LoanCalculatorTest.monthlyPaymentMatchesAnnuityFormula[2]` (100000, 13 %, 12) | 8931.73 | 8929.85 | BUG-16 (BL-10) |
| `…monthlyPaymentMatchesAnnuityFormula[3]` (10000, 7 %, 6) | 1700.86 | 1700.66 | BUG-16 |
| `…monthlyPaymentMatchesAnnuityFormula[4]` (1000000, 40 %, 60) | 38751.64 | 38725.09 | BUG-16 |
| `LoanCalculatorTest.boundaryParametersAreAccepted[1]`, `[2]` (n = 3) | без винятку | IllegalArgumentException «Строк має бути в межах 3..60» | BUG-17 (BL-30) |
| `TransferValidatorTest.dailyTotalUpToLimitIsAllowed[2]` (40000 + 10000.00) | `[]` | `[DAILY_LIMIT_EXCEEDED]` | BUG-18 (BL-40) |
| `TransferValidatorTest.invalidToIbanIsReported` | `[INVALID_TO_IBAN]` | `[]` | BUG-19 (BL-50) |
| `AccountLedgerTest.withdrawUpToBalanceIsAllowed[2]` (100.00 з 100.00) | баланс 0.00 | IllegalStateException | BUG-20 (BL-70) |
| `AccountLedgerTest.historyIsInChronologicalOrder` | DEPOSIT, WITHDRAWAL | WITHDRAWAL, DEPOSIT | BUG-21 (BL-70) |
| `AccountLedgerTest.overdraftIsRejectedAndBalanceUnchanged` | IllegalArgumentException | IllegalStateException | BUG-22 (BL-70) |

## Стабільність UI-набору
3 прогони поспіль 24.11.2026 (15:3x): UI-тести 6/6 passed у кожному; результати юніт-тестів однакові в усіх прогонах.
