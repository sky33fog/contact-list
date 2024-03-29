
# Contact list


## Описание
Приложение является практической работой 3 модуля курса "Разработка на Spring Framework" 
образовательной платформы Skillbox.

Проект представляет собой веб-приложение, которое через интерфейс пользователя позволяет:
- просматривать список имеющихся контактов;
- создавать новые контакты;
- удалять и редактировать имеющиеся контакты. 


## Настройка приложения
Параметры работы приложения задаются в конфигурационном файле **application.yml**:
- **app.storage-type** параметр отвечающий за выбор типа хранилища для хранения контактов.
Для выбора базы данных необходимо назначить значение **db**, для выбора оперативной памяти - **ram**.
- **app.contacts-generator.enabled** параметр отвечающий за использование генератора, наполняющего хранилище тестовыми данными.
  Для включения генератора необходимо указать значение **true**, для отключения - **false**.

## Запуск приложения осуществляется  средствами среды разработки
Для запуска приложения необходимо выполнить последоватьельность действий: 
- в файле **application.yml** задать тип хранилища данных и необходимость генерации данных для наполнения хранилища;
- если на машине отсутствует docker-образ _postgres 12.3_ - скачать его командой _docker pull postgres:12.3_
- запустить базу данных для работы приложения командой:
  - _**docker compose up**_ из директория **docker**, проекта;
- запустить приложение средствами среды разработки.