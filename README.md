# Prepare

## 一个回归职场的 Android / Kotlin 练习项目

`Prepare` 是一个用于复习 Kotlin 和 Android 开发基础的个人练习项目。项目主要用于回顾 Android 页面开发、常见 Jetpack 组件、网络请求、本地存储、列表展示、UI 状态管理以及基础工程结构搭建。

项目当前以 Kotlin 为主要开发语言，结合 Jetpack Compose、传统 View、Room、Retrofit、OkHttp 等技术，用于系统性复习 Android 开发常见能力。

---

## 项目目标

本项目主要用于：

- 回顾 Kotlin 基础语法和 Android 开发基础。
- 练习 Android 页面搭建与组件使用。
- 熟悉 MVVM 分层思想和 UI 状态管理。
- 练习协程、Flow、StateFlow、SharedFlow 等异步与状态流能力。
- 练习 Repository、本地缓存、Room 数据库和网络请求封装。
- 复习 RecyclerView、ListAdapter、DiffUtil 等列表相关知识。
- 积累面试中常见的 Android 项目表达材料。

---

## 技术栈

### 开发语言

- Kotlin

### UI 层

- Jetpack Compose
- Material3
- XML View / ViewBinding
- ConstraintLayout
- RecyclerView
- SwipeRefreshLayout

### 架构与状态管理

- MVVM
- ViewModel
- Lifecycle
- Flow
- StateFlow
- SharedFlow
- UIState

### 网络请求

- Retrofit
- OkHttp
- Gson Converter
- OkHttp Logging Interceptor

### 本地存储

- Room
- Room KTX
- KSP

### 测试相关

- JUnit
- AndroidX Test
- Espresso
- Robolectric
- Compose UI Test
- MockWebServer

### 构建工具

- Gradle Kotlin DSL
- Version Catalog
- Android Gradle Plugin
- KSP

---

## 项目结构说明

当前项目采用单模块结构，主要模块为 `app`。

```text
Prerpare/
├── app/                              # Android 应用主模块
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/prerpare/
│   │   │   │   ├── data/             # 数据层：Repository、网络、本地数据源等
│   │   │   │   ├── model/            # 数据模型：实体类、接口响应模型、UI Model 等
│   │   │   │   ├── ui/               # 页面层：Activity、Compose 页面、Adapter 等
│   │   │   │   └── utils/            # 工具类、扩展函数、通用方法等
│   │   │   ├── res/                  # 资源文件：布局、图片、字符串、主题等
│   │   │   └── AndroidManifest.xml   # 应用清单文件
│   │   ├── test/                     # 本地单元测试
│   │   └── androidTest/              # Android 仪器测试
│   └── build.gradle.kts              # app 模块 Gradle 配置
│
├── gradle/
│   └── libs.versions.toml            # Version Catalog，统一管理依赖版本
│
├── build.gradle.kts                  # 项目级 Gradle 配置
├── settings.gradle.kts               # Gradle 项目与模块配置
├── gradle.properties                 # Gradle 全局配置
├── gradlew                           # Linux / macOS Gradle Wrapper
├── gradlew.bat                       # Windows Gradle Wrapper
└── README.md                         # 项目说明文档
```

---

## 分层说明

### data

`data` 层主要负责数据获取和数据管理，适合放置：

- Repository
- RemoteDataSource
- LocalDataSource
- Room DAO
- 网络请求接口
- 缓存逻辑

Repository 作为数据访问入口，对 ViewModel 屏蔽具体数据来源。数据可以来自网络、本地数据库或缓存。

### model

`model` 层主要负责定义项目中的数据结构，例如：

- 接口响应数据类
- Room Entity
- 页面展示用 UI Model
- 请求参数模型

### ui

`ui` 层主要负责页面展示和用户交互，例如：

- Activity
- Fragment
- Compose Screen
- RecyclerView Adapter
- ViewHolder
- UIState
- UiEvent

UI 层只负责渲染状态和上报用户事件，不直接处理复杂业务逻辑。

### utils

`utils` 层主要存放通用工具能力，例如：

- 扩展函数
- 日志工具
- 时间格式化
- 通用常量
- 网络状态辅助方法

---

## 核心学习点

本项目重点练习以下 Android 开发能力：

### 1. MVVM 架构

通过 ViewModel 管理页面状态，Repository 统一管理数据来源，UI 层只负责渲染和事件分发。

典型数据流：

```text
UI 触发事件
    ↓
ViewModel 处理业务逻辑
    ↓
Repository 获取数据
    ↓
网络 / 本地数据库 / 缓存
    ↓
ViewModel 更新 UIState
    ↓
UI 根据状态重新渲染
```

### 2. UIState 状态管理

使用统一的 UIState 管理页面状态，例如：

```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}
```

统一 UIState 的价值：

- 避免页面中散落大量 boolean 状态。
- 保证 Loading、Success、Error、Empty 等状态互斥。
- 让页面渲染逻辑更清晰。
- 方便测试和维护。

### 3. 协程与 Flow

项目学习重点包括：

- viewModelScope
- launch
- async
- withContext
- Dispatchers.Main / IO / Default
- Flow
- StateFlow
- SharedFlow
- 协程异常处理
- 协程取消机制

常见使用方式：

```kotlin
viewModelScope.launch {
    try {
        val data = withContext(Dispatchers.IO) {
            repository.getData()
        }
        _uiState.value = UiState.Success(data)
    } catch (e: Exception) {
        _uiState.value = UiState.Error("加载失败")
    }
}
```

### 4. StateFlow 与 SharedFlow

- `StateFlow`：用于保存页面状态，例如 Loading、Success、Error。
- `SharedFlow`：用于发送一次性事件，例如 Toast、Snackbar、页面跳转。

推荐方式：

```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
val uiState: StateFlow<UiState> = _uiState

private val _eventFlow = MutableSharedFlow<UiEvent>()
val eventFlow: SharedFlow<UiEvent> = _eventFlow
```

### 5. Repository 与缓存策略

Repository 层可以实现以下数据策略：

```text
1. 先读取本地缓存
2. 再请求网络数据
3. 网络成功后更新本地数据库
4. 网络失败时保留旧数据
```

这种方式可以提升页面响应速度，并增强弱网场景下的可用性。

### 6. RecyclerView 与 ListAdapter

项目中引入 RecyclerView，可用于练习：

- RecyclerView 基本使用
- Adapter / ViewHolder
- ListAdapter
- DiffUtil
- 局部刷新
- 避免 notifyDataSetChanged
- 列表性能优化

推荐使用：

```kotlin
class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
```

### 7. Compose 基础

项目已开启 Jetpack Compose，可用于练习：

- @Composable
- Modifier
- Column / Row / Box
- remember
- rememberSaveable
- 状态提升
- StateFlow + Compose 状态收集
- Compose 与传统 View 的区别

---

## 环境要求

建议使用以下环境运行项目：

- Android Studio：最新稳定版本
- JDK：11 或以上
- Gradle：使用项目自带 Gradle Wrapper
- minSdk：24
- targetSdk：36
- compileSdk：36
- Kotlin：2.2.10
- Android Gradle Plugin：9.1.0

---

## 安装与运行

### 1. 克隆项目

```bash
git clone https://github.com/guoshiqi/Prerpare.git
```

### 2. 进入项目目录

```bash
cd Prerpare
```

### 3. 使用 Android Studio 打开项目

打开 Android Studio，选择：

```text
File -> Open -> 选择 Prerpare 项目根目录
```

### 4. 等待 Gradle Sync

首次打开项目时，Android Studio 会自动同步 Gradle 依赖。

如果同步失败，可以检查：

- JDK 版本是否正确。
- Android Studio 版本是否过低。
- 网络是否可以访问 Google Maven 和 Maven Central。
- Gradle Wrapper 是否可用。

### 5. 运行项目

连接 Android 设备或启动模拟器，然后点击 Android Studio 顶部的 `Run` 按钮运行项目。

也可以使用命令行构建 Debug 包：

```bash
./gradlew assembleDebug
```

Windows 环境：

```bash
gradlew.bat assembleDebug
```

---

## 依赖管理

项目使用 `gradle/libs.versions.toml` 统一管理依赖版本。

示例：

```toml
[versions]
kotlin = "2.2.10"
room = "2.7.0"
retrofit2 = "3.0.0"
okhttp3 = "5.3.0"
```

新增依赖时，建议优先在 `libs.versions.toml` 中声明版本和库别名，然后在 `app/build.gradle.kts` 中引用，避免依赖版本散落在多个 Gradle 文件中。

---

## 当前功能

当前项目主要包含：

- Kotlin 基础练习
- Android 页面练习
- Compose 基础练习
- RecyclerView / 列表相关练习
- 网络请求相关练习
- Room 本地存储相关练习
- Android 面试知识点沉淀

---

## 后续计划

后续可以继续补充：

- MVVM 页面示例
- Repository 缓存策略示例
- Room 数据库完整 CRUD 示例
- Retrofit 网络请求封装
- StateFlow + UIState 页面状态管理
- SharedFlow 处理 Toast / 页面跳转事件
- RecyclerView ListAdapter 示例
- Compose 页面重构
- 单元测试与 MockWebServer 示例
- Crash / ANR / 内存泄漏稳定性检查示例

---

## 面试表达

这个项目可以在面试中这样介绍：

> 这是一个基于 Kotlin 的 Android 练习项目，主要用于复习 Android 基础开发和现代 Jetpack 技术栈。项目采用单模块结构，使用 Gradle Kotlin DSL 和 Version Catalog 管理构建和依赖。UI 层同时练习传统 View 和 Jetpack Compose，数据层计划通过 Repository 统一管理网络、本地数据库和缓存。网络请求使用 Retrofit + OkHttp，本地持久化使用 Room，异步处理使用 Kotlin 协程、Flow、StateFlow 和 SharedFlow。项目重点不是做复杂业务，而是系统性复习 Android 页面开发、状态管理、列表优化、本地缓存、网络请求和项目稳定性相关知识。

---

## 许可证

本项目目前为个人学习项目，暂未指定开源许可证。