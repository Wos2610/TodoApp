# TodoApp
## 1. Introduction 
- The TodoApp is a product designed for efficient personal task management, offering a range of features such as inserting, editing, deleting, searching, sorting, filtering, and grouping tasks.
- Tasks can be categorized into predefined categories such as Default, Work, and Study. Additionally, users have the flexibility to create custom categories tailored to their specific needs.
- Displaying tasks based on dates enhances user convenience in managing their time effectively.
## 2. Technologies
- Language: Kotlin
- Room Database, Flow
- MVVM Architecture
- ViewModel, LiveData, ViewBinding
- Navigation Component
- TabLayout, ViewPager2
## 3. Libraries & Dependencies
- [Support libraries](https://developer.android.com/jetpack/androidx): AppCompat / RecyclerView / ConstraintLayout / ViewPager2 / CardView
- [Material Design 3](https://m3.material.io/components): TextInputLayout / TabLayout /  / FloatingActionButton / DatePickers / TimePickers / Dialogs / NavigationBar
- [Glide](https://github.com/bumptech/glide): An image loading and caching library for Android focused on smooth scrolling
- [FloatingSearchView](https://github.com/arimorty/floatingsearchview): Cute library to implement SearchView in a Material Design Approach
- [Calendar](https://github.com/kizitonwose/Calendar): A highly customizable calendar library for Android
## 3. Demo App
### a. Home 
- **Home** screen contains: search bar, category list, today task list:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/98f5cebe-ccfd-47a3-992d-0f4ef800898f/home.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/953accf3-1003-4d32-8b18-e39008b2b319/edit_delete_home.jpg" width="200" />

- Searching task:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/14b70f93-daa0-4775-b0e0-4805b71ec747/search_task.jpg" width="200" />

- When choosing **View All** in the **Category** screeen:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/0fce3fb2-ddb1-4ebd-b51a-b650b9d65f7e/all_categories.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/6f50a9e5-cd6e-429c-bf18-85f6a649ed8a/edit_delete_all_category.jpg" width="200" />

- When choosing **View All** in **Today Task**:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/5b8df453-8ba7-42e8-b4f7-fe663af731a3/all_tasks.jpg" width="200" />

- When selecting a category, the application will display tasks that belong to that specific category:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/772ffb52-4b32-4b4e-a517-5c0031f1cf20/task_group_by_category.jpg" width="200" />

- Adding a new category:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/23b99326-fa60-48ef-ba97-2b600ecfceb3/new_category.jpg" width="200" />

- Editing a category:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/55a6f90d-40ef-4b73-9cef-a6dd5d0d5d49/edit_category.jpg" width="200" />

### b. Task 
- The tasks are divided into three tab layouts: TODO, On Progress, and Done:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/ed0bc0c0-8141-4b22-ae86-b035a3cd5ed3/to_do_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/a3ae85df-20cc-4eb1-ab78-3f98dbe7dea0/on_progress_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/2e7cef44-1c73-4903-89a4-6844f4211057/done_task.jpg" width="200" />

- Adding a new task:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/92d2086a-9eeb-4a39-96a2-923257fd201b/new_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/14a0d204-e94a-47e7-8c00-0b897a65a565/choose_category_new_task.jpg" width="200" />

- Archiving a task:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/7e365a88-7d4c-4fe8-a524-09c3f4fcc9c7/archive_tab_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/f17237ff-29c1-47c1-b959-1096aee61fd5/confirm_dialog_archive_task.jpg" width="200" />
<img src="(https://github.com/Wos2610/TodoApp/assets/101249227/0d6ff897-c86e-4748-bf1f-6c780c6bd22f/restore_archive_task.jpg" width="200" />

- Editing a task:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4c6037e1-e8c9-4bec-a002-cf90e082f296/edit_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/53f08954-1489-4c82-8454-c7b6d42cb389/confirm_dialog_back_button.jpg" width="200" />

- Sorting list of tasks:
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/00540ba7-414c-4ead-886e-cbdb45c89ed4/sort_task.jpg" width="200" />

### c. Calendar 
- Users have the option to select and display tasks based on a specific date.
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/9b0ca721-fc90-4178-8140-95de8e380498/today_calendar.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/c9046ed8-3d5b-4428-a963-68eff6e5cc7e/choose_date_calendar.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/d8cd9b2c-e648-46c2-931a-96f777916a48/choose_day_calendar.jpg" width="200" />

### d. Archive
- In the Archive screen, tasks can either be permanently deleted or restored.
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4e4d3cf8-ccf0-4a47-8a12-3a745abff602/no_archive_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/26dcaec1-4f3a-4054-8298-4c3bd92eb56b/archive.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/4a1663ff-ff57-4fa5-9e6b-c1e5e5d57a98/delete_archive_task.jpg" width="200" />
<img src="https://github.com/Wos2610/TodoApp/assets/101249227/f2747db5-62cf-4f84-b349-5733e593fd72/restore_archive_task.jpg" width="200" />

