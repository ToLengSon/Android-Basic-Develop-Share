- ### 开发语言

  - Java
  - Kotlin

- ### 开发工具

  - IDE：android studio

  - Simulator：
    - android studio自带的
    - Genymotion（基于Virtual Box虚拟机）[可选, 性能高于自带模拟器]

- ### Mainfest.xml

  - 类似于iOS中的info.plist
  - 向系统注册组件，供于系统查询，此机制可以实现跨app

- ### 四大组件

  - #### Activity

    - 每一个activity就是一个软件界面，类似于iOS的UIViewController，但需要在manifest.xml中配置才可使用

    - 每一个activity都可以对应布局资源文件，用于快速布局软件界面，类似于iOS的SB/Xib

    - 通过Intent(意图)进行页面间的传值跳转，可跨app；由ActivityManager管理
      - Intent可分为显示与隐式，指定类名的称为显示intent，指定action、uri、category、data的等称为隐式intent；可跨app跳转
    - 生命周期
      - onCreate	=== viewDidLoad，创建，一次性的资源初始化
      - onStart === viewWillAppear，可见
      - onResume === viewDidAppear，获得焦点
      - onPause === viewWillDisappear，失去焦点
      - onStop === viewDidDisappear，不可见
      - onRestart：当Activity重新成为栈顶活动时被调用，之后调用onStart方法
      - onDestroy === dealloc

  - #### Service

    - 使用方式：创建类继承Service，并且在manifest.xml中配置
    - 无UI，用在后台任务操作，例如：后台播放音乐，升级时下载apk等；
    - 启动的基本操作：
      - 启动服务：context.startService(intent, service.class);
      - 停止服务：context.stopService(intent, service,class);
      - 绑定服务：context.bindService(intent, connect, flag);
      - 解绑服务：context.unbindService(connect);
    - 默认在主线程中执行，如Service执行耗时操作，请在子线程中开启服务，以免阻塞主线程导致ANR(Application Not Responding)异常
    - 多个进程可使用同一个Service，需要使用AIDL(android接口定义语言)进行通信

  - #### Broadcast Receiver

    - 使用方式：创建类继承BroadcastReceiver，静态注册即在manifest.xml中配置，动态注册在代码中调用Context.registerReceiver()方法
    - 当某事放生时，通知感兴趣的接受者；类似iOS中的NSNotificationCenter
    - 有序广播(同步广播)：可设置优先级，接受者优先级高的先接收到广播，并且可截断广播，使其他优先级低的接受者无法收到该广播；通过这个可以实现android短信拦截
    - 无序广播(异步广播)：无优先级，接受者接收到广播的顺序不定
    - App应用内广播: LocalBroadcastManager单例对象管理，作用于当前应用内，其他app无法监听
    - ...

  - #### Content Provider

    - 应用向外部(其他app)提供数据的方式进行抽象化的接口被称为Content Provider，其他app通过该接口来操作当前app内部数据
    - 

- ### 七大布局

  - LinearLayout 线性布局
    - android:orientation(布局方向): horizontal(水平)/vertical(垂直)
    - android:layout_weight: 权重
    - android:layout_gravity: 在父视图中对齐方式
    - android:gravity: 视图内容对齐方式
    - 实际项目中经常使用
  - TableLayout 表格布局
    - 类似于HTML中的\<table\>、\<tr\>、\<td\>标签，也可合并单元格
    - 每一行都是一个TableRow视图容器，表格的列数也就是该行容纳的视图的个数，在列中的视图宽度都被设置为wrap_content
    - android:collapseColumns: 隐藏的列，索引从0开始
    - android:stretchColumns: 可拉伸的列，填充该行剩余空间，但是最小的宽度保证是wrap_content的
    - android:shrinkColunms: 可收缩的列，当行空间不足时，被压缩
    - 实际项目中使用频率低
  - FrameLayout 帧布局
    - 层叠的方式添加视图，先添加的在最底层，最后一个添加的在视图层次最顶层
    - android:layout_gravity: 在父视图中对齐方式
    - 功能单一，性能相对于其他布局略高，在需要仅仅盛装子视图时可以使用；实际项目中使用频率低
  - AbsoluteLayout 绝对布局
    - android:layout_x: 距离父视图左边距离，以父视图左上角为原点
    - android:layout_y: 距离父视图顶部距离，以父视图左上角为原点
    - 实际项目中几乎不用，都被Google废弃了
  - RelativeLayout 相对布局
    - android:layout_below: 在目标视图的下方
    - android:layout_above: 在目标视图的上方
    - android:layout_toLeftOf: 在目标视图的左边
    - android:layout_toRightOf: 在目标视图的右边
    - android:layout_alignTop: 与目标视图顶部对齐
    - android:layout_alignBottom: 与目标视图底部对齐
    - android:layout_alignLeft: 与目标视图左对齐
    - android:layout_alignRight: 与目标视图右对齐
    - android:layout_alignBaseLine: 与目标视图基线对齐
    - android:layout_alignParentRight: 与父视图右对齐
    - android:layout_alignParentLeft: 与父视图左对齐
    - android:layout_alignParentTop: 与父视图顶部对齐
    - android:layout_alignParentBottom: 与父视图底部对齐
    - android:layout_centerInParent: 水平垂直居中于父视图？true/false
    - android:layout_centerVertical: 垂直居中于父视图？true/false
    - android:layout_centerHorizontal: 水平居中于父视图？true/false
    - 实际项目中经常使用
  - GridLayout 网格布局
    - 不熟悉，貌似具有TableLayout的功能，但是比TableLayout更方便，可以设置行数与列数，并且可以做类似iOS的UICollectionView所做的功能；具体用法自行Google、百度
  - ConstraintLayout 约束布局
    - Google推荐，现在创建布局文件，默认容器是ConstraintLayout，不详解，因为我不会，感兴趣的可以自行Google、百度

- ### 常用第三方

  - Butter Knife

    - 为了解决重复性的findViewById，类比于iOS的@IBOutlet, @IBAction
  - Glide

    - 图片加载框架
  - Retrofit

    - 网络请求框架
  - ……

- ### 推荐书籍

  - Java SE篇：《Java核心技术》(卷1、卷2)

  - Android篇：《第一行代码——android》

