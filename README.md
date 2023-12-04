# TodoApp
## 1. Giới thiệu về TodoApp
- TodoApp là sản phẩm dùng để quản lý task hiệu quả cho cá nhân.
- Cung cấp các tính năng: insert, edit, delete, sort, filter, group các tasks.
- Các task có thể chia vào các category có sẵn: Default, Work, Study; hoặc user có thể tự tạo ra các category cho riêng mình.
- Hiển thị các task theo ngày giúp user thuận tiện hơn trong việc quản lý thời gian của bản thân.
## 2. App sử dụng
- Ngôn ngữ: Kotlin
- Room Database
- Mô hình MVVM
- ViewModel, LiveData
- Navigation Component
- TabLayout, ViewPager2
## 3. Demo App
### a. Home 
- Màn hình **Home** sẽ bao gồm: search bar, category list, today task list
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/98f5cebe-ccfd-47a3-992d-0f4ef800898f/home.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/953accf3-1003-4d32-8b18-e39008b2b319/edit_delete_home.jpg" width="200" />

- Search task
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/14b70f93-daa0-4775-b0e0-4805b71ec747/search_task.jpg" width="200" />

- Khi chọn **View All** ở **Category**:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/0fce3fb2-ddb1-4ebd-b51a-b650b9d65f7e/all_categories.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/6f50a9e5-cd6e-429c-bf18-85f6a649ed8a/edit_delete_all_category.jpg" width="200" />

- Khi chọn **View All** ở **Today Task**:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/5b8df453-8ba7-42e8-b4f7-fe663af731a3/all_tasks.jpg" width="200" />

- Khi chọn một category, hiển thị các task thuộc category này
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/772ffb52-4b32-4b4e-a517-5c0031f1cf20/task_group_by_category.jpg" width="200" />

- Add new category
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/23b99326-fa60-48ef-ba97-2b600ecfceb3/new_category.jpg" width="200" />

- Edit category
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/55a6f90d-40ef-4b73-9cef-a6dd5d0d5d49/edit_category.jpg" width="200" />

### b. Task 
- Các task chia thành 3 tablayout: TODO, On Progress, Done
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/ed0bc0c0-8141-4b22-ae86-b035a3cd5ed3/to_do_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/a3ae85df-20cc-4eb1-ab78-3f98dbe7dea0/on_progress_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/2e7cef44-1c73-4903-89a4-6844f4211057/done_task.jpg" width="200" />

- Add new task
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/92d2086a-9eeb-4a39-96a2-923257fd201b/new_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/14a0d204-e94a-47e7-8c00-0b897a65a565/choose_category_new_task.jpg" width="200" />

- Archive task
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/7e365a88-7d4c-4fe8-a524-09c3f4fcc9c7/archive_tab_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/f17237ff-29c1-47c1-b959-1096aee61fd5/confirm_dialog_archive_task.jpg" width="200" />
<img src="(https://github.com/Wos2610/TodoApp/assets/101249227/0d6ff897-c86e-4748-bf1f-6c780c6bd22f/restore_archive_task.jpg" width="200" />

- Edit task
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4c6037e1-e8c9-4bec-a002-cf90e082f296/edit_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/53f08954-1489-4c82-8454-c7b6d42cb389/confirm_dialog_back_button.jpg" width="200" />

- Sort task
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/00540ba7-414c-4ead-886e-cbdb45c89ed4/sort_task.jpg" width="200" />

### c. Calendar 
- Có thể chọn hiển thị task theo ngày cụ thể
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/9b0ca721-fc90-4178-8140-95de8e380498/today_calendar.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/c9046ed8-3d5b-4428-a963-68eff6e5cc7e/choose_date_calendar.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/d8cd9b2c-e648-46c2-931a-96f777916a48/choose_day_calendar.jpg" width="200" />

### d. Archive
- Task khi trong Archive thì có thể delete vĩnh viễn hoặc là restore lại.
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4e4d3cf8-ccf0-4a47-8a12-3a745abff602/no_archive_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/26dcaec1-4f3a-4054-8298-4c3bd92eb56b/archive.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4a1663ff-ff57-4fa5-9e6b-c1e5e5d57a98/delete_archive_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/f2747db5-62cf-4f84-b349-5733e593fd72/restore_archive_task.jpg" width="200" />

