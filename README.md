# TaskMGR

## 概要
TaskMGRは、各メンバのタスクを共有することで、チーム全体のタスクが可視化される為、期限超過や抜け漏れを未然に防ぐ。<br>
また、各メンバのタスク量や進捗が目に見えてわかるようになることから、タスク量が多いメンバの負担を、空いているメンバにて巻き取りしやすくなるタスク管理アプリケーションです。

<br>

## 使用技術
<img src="https://img.shields.io/badge/-HTML5-2f2f2f.svg?logo=html5&style=flat"> <img src="https://img.shields.io/badge/-CSS3-2f2f2f.svg?logo=css3&style=flat"> <img src="https://img.shields.io/badge/JavaScript-2f2f2f.svg?logo=javascript&style=flat"> <img src="https://img.shields.io/badge/Java-2f2f2f.svg?logo=Java&style=flat">
<br>
<img src="https://img.shields.io/badge/MySQL-2f2f2f.svg?logo=mysql&style=flat">
<br>
<img src="https://img.shields.io/badge/Spring Boot-2f2f2f.svg?logo=springboot&style=flat">
<img src="https://img.shields.io/badge/Spring Security-2f2f2f.svg?logo=springsecurity&style=flat">
<br>
<img src="https://img.shields.io/badge/Thymeleaf-2f2f2f.svg?logo=thymeleaf&style=flat">
<img src="https://img.shields.io/badge/Gradle-2f2f2f.svg?logo=gradle&style=flat">
<img src="https://img.shields.io/badge/Eclipse IDE-2f2f2f.svg?logo=eclipseide&style=flat">

<br>

## 機能一覧

|新規アカウント作成機能|ユーザ編集・削除機能|
|---|---|
|![UserRegister-Validation](https://github.com/user-attachments/assets/b0e14dbb-74cd-4d6e-aa12-8fc571b55ce4)|![User-Edit-Delete](https://github.com/user-attachments/assets/860c4b51-0672-4580-80c5-3bc0a239bdc6)
* **新規アカウント作成機能**
    * ログイン画面の「新規アカウント作成」から、各必須項目入力後「登録」をクリックすることでアカウント登録を行えます。
    * バリデーション機能によって、未入力の必須項目がある場合、正しい値が入力されていない場合はアカウントの登録が行えません。
* **ユーザ編集・削除機能**
    * マイページ画面の「ユーザ情報」から「編集」をクリックすることでログインユーザ情報の編集が可能になり、「確定」をクリックすることで更新されます。
    * マイページ画面の「ユーザ管理」から削除したいユーザを選択し、「削除」をクリックすることで削除されます。
    * ユーザ削除機能は役職「リーダ」にのみ権限が与えられているため、「メンバ」のマイページには「ユーザ管理」が表示されません。
 
|ログイン・ログアウト機能|メンバ役職別表示切替・名前検索機能|
|---|---|
![Login-Logout-Validation](https://github.com/user-attachments/assets/db04e958-e32b-4b1f-9d87-f4ad75022242)|![Member-JobDisplay-Search](https://github.com/user-attachments/assets/ab47d55c-df94-4f8e-91f2-ab6f44709232)|
* **ログイン・ログアウト機能**
   * アカウント登録済みのユーザは「Email」と「Password」を入力し、「ログイン」をクリックすることでログインできます。
   * バリデーション機能によって、未入力の項目やアカウント登録されていない「Email/Password」ではログインできません。
* **メンバ役職別表示切替・名前検索機能**
   * メンバ一覧画面「表示切替」のチェックボックスに表示したい役職にチェックをつけることで該当役職ユーザが表示されます。
   * メンバ一覧画面「名前検索」の入力欄に検索したい文字列を入力し、「検索」をクリックすることで該当するユーザが表示されます。

|新規タスク追加機能|タスク編集・削除機能|
|---|---|
|![TaskRegister-Validation](https://github.com/user-attachments/assets/9e682082-f4b2-4912-bfc1-f93d2b5401d9)|![TaskEdit-Delete](https://github.com/user-attachments/assets/8cc2710e-84dc-4242-955c-16417d645e58)
* **新規タスク追加機能**
   * タスク一覧画面「新規タスク追加」から、各必須項目入力後「追加」をクリックすることでタスクの追加が行えます。
   * バリデーション機能によって、未入力の必須項目がある場合、正しい値が入力されていない場合はタスクの追加が行えません。
* **タスク編集・削除機能**
   * タスク一覧より編集したいタスクをクリックすることでモーダルウィンドウが表示されます。「×」または「ウィンドウ範囲外」をクリックすることでモーダルウィンドウが閉じます。
   * モーダルウィンドウ内の「編集」をクリックすると編集モードになり、「キャンセル」をクリックすると閲覧モードになります。「確定」をクリックすることで更新されます。
   * モーダルウィンドウ内の「削除」をクリックすることでタスクの削除ができます。

|タスク状態別表示切替機能|担当者割り当て機能|
|---|---|
|![TaskStatus-Display](https://github.com/user-attachments/assets/fa9ac5c9-cdb4-4f5a-b613-58a5d72d3f5d)|![Task-Assign](https://github.com/user-attachments/assets/10753882-a682-460f-864f-1e10eb2158cc)|
* **タスク状態別表示機能**
   * タスク一覧画面「状態別表示」をクリックするとチェックボックスが表示され、表示したい状態のチェックボックスにチェックをつけることで該当状態タスクが表示されます。
* **担当者割り当て機能**
   * 未割振りタスクに担当者を割り当てる、または現在の担当者から担当者を変更するには、タスク編集機能を用いることで個別で変更することができます。
   * 全体タスク一覧画面の「担当未割振りタスク」をクリックし、担当欄のドロップダウンリストから担当者を選択し「更新」をクリックすることで、一度に複数タスクへ割り当てることができます。

|タスク検索機能|ページネーション機能|
|---|---|
|![Task-Search](https://github.com/user-attachments/assets/6f07e40c-f177-44a6-ad29-b115dd1ed4c5)|![Pagenation](https://github.com/user-attachments/assets/3ef5b437-839f-4c15-adaf-004b99443718)|
* **タスク検索機能**
   * タスク一覧画面「検索」をクリックすると各項目の入力欄が表示されます。検索したい値・文字列を各項目に入力し「検索」をクリックすることで該当タスクが表示されます。
* **ページネーション機能**
   * 表示するタスクが「10個」、ユーザが「5個」を超える場合、ページネーション機能によって次のページに表示されるようになっています。
   * 表示しているページが、「最前」または「最後」の場合、「＜」または「＞」をクリックできないようになっています。

<br>

## データベース作成クエリ
```mysql
CREATE DATABASE task_management_db;

USE task_management_db;

CREATE TABLE role (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE task_state (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE task_priority (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE user (
    id INTEGER NOT NULL PRIMARY KEY auto_increment,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(128) NOT NULL,
    role INTEGER NOT NULL
);

CREATE TABLE task (
    id INTEGER NOT NULL PRIMARY KEY auto_increment,
    name VARCHAR(128),
    priority INTEGER,
    due_time DATETIME,
    status INTEGER,
    user_id INTEGER
);

INSERT INTO role (id,name) VALUES (1,'ROLE_GENERAL'),(2,'ROLE_ADMIN');
INSERT INTO task_priority (id,name) VALUES (1,'高'),(2,'中'),(3,'低');
INSERT INTO task_state (id,name) VALUES (1,'未着手'),(2,'進行中'),(3,'完了済');
```
