# SUAIChatServer
Сервер для простенького чата на Kotlin + Spring. Разрабатывался специально для подготовки сборной ГУАП к региональному этапу WorldSkills по мобильной разработке. На этом конкурсе
в задании всегда есть работа с REST API. Я предложил написать простенький чат. Да, нормальный серер делается иначе, многие решения (авторизация, хранение сообщений, обмен данными
с клиентом) необходимо делать более абстрактно и более серьёзно. Например использовать WebSockets или какой-нибудь сервис (к примеру Firebase)
для передачи информации в реальном времени на клиент. К тому же это мой первый проект на Spring и третий на Kotlin. Прошу не судить очень строго )

# Документация по API сервера SUAI Chat Application

*Сервер с API доступен по адресу* `fspobot.tw1.ru:8080`
Сервер может возвращать стандартные коды запросов, например: `HTTP STATUS CODE 403 (UNAUTHORIZED)`

## Авторизация ( /auth )



### Регистрация ( /register )

Метод: **POST**
Аргументы запроса: нет
Параметры заголовка: нет
Тело запроса: JSON в следующем формате:
`{
    	"firstName": "name1", 
    	"lastName": "name2",
    	"email": "anymail@mail.com",
    	"password": "anypassword"
}`

Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON
` {"state": "ok", "token":"acces_token" }`



### Авторизация ( /login )

Метод: **POST**
Аргументы запроса: нет
Параметры заголовка: нет
Тело запроса: JSON в следующем формате:
`{
    	"email": "anymail@mail.com",
    	"password": "anypassword"
}`

Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON
` { "state": "ok", "token": "acces_token" }`



### Выход ( /logout )

Метод: **POST**
Аргументы запроса: нет
Параметры заголовка: Токен `Authorization`
Тело запроса: нет
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха



## Чаты ( /chats )



### Получение списка зарегестрированных пользователей ( /allusers )

Метод: **GET**
Аргументы запроса: нет
Параметры заголовка: Токен `Authorization`
Тело запроса: нет
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON в следующем формате
`
    [
        {
            "firstName": "Andrey",
            "lastName": "Bogdanov",
            "id": 1
        },
        {
            "firstName": "Alex",
            "lastName": "Titov",
            "id": 2
        }
    ]
`



### Получение списка чатов пользователя ( /myChats )

Метод: **GET**
Аргументы запроса: нет
Параметры заголовка: Токен `Authorization`
Тело запроса: нет
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON в следующем формате
`[
        {
            "chatIconName": null,
            "chatName": "Тупой чат",
            "id": 1
        },
        {
            "chatIconName": "fjdsaljnf21e1n421m.png",
            "chatName": " чат",
            "id": 2
        },
]`

### Создание чата ( /createChat )

Метод: **POST**
Аргументы запроса: нет
Параметры заголовка: токен `Authorization`
Тело запроса: `{ "chatName": "_MY_CHAT_NAME_", "userIds": [1, 2, 3, 4 ... ] }`
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON в следующем формате
`{
	"chatIconName": null,
    "chatName": "Тупой чат",
    "id": 1,
    "users": [
        {
            "firstName": "firstuser",
            "lastName": "12345",
            "id": 2
        },
        {
            "firstName": "secondU2ser",
            "lastName": "12345",
            "id": 3
        } ...
}`

### Отправка сообщения ( /sendMessage )

Метод: **POST**
Аргументы запроса: нет
Параметры заголовка: токен `Authorization`
Тело запроса: `{ "chatId": 1, "message": "Any text in message" }`
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON в следующем формате:
`{
	"chatIconName": null,
    "chatName": "Тупой чат",
    "id": 1,
    "users": [
        {
            "firstName": "firstuser",
            "lastName": "12345",
            "id": 2
        },
        {
            "firstName": "secondU2ser",
            "lastName": "12345",
            "id": 3
        } ...
}`

### Получение сообщений ( /getMessages )

Метод: **GET**
Аргументы запроса: `chatId` - id чата из которого вытаскиываем сообщения
Параметры заголовка: токен `Authorization`
Тело запроса: -
Возвращаемые значения:
`HTTP STATUS CODE 200` - в случае успеха, а также JSON в следующем формате:
`{
	"chat": {
        "chatIconName": null,
        "chatName": "Тупой чат",
        "id": 1,
        "users": [
        {
            "firstName": "firstuser",
            "lastName": "12345",
            "id": 2
        },
        {
            "firstName": "secondU2ser",
            "lastName": "12345",
            "id": 3
        } ...
	},
	"user": {
			"firstName": "firstuser",
            "lastName": "12345",
            "id": 2
	},
	"content": "Message text",
	"date": Date object same as java.util.date
}`
