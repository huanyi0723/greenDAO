# greenDAO 使用方法

# 简介
ORM（object / relational mapping）框架
是对sqlite数据库访问的对象化封装。以对象的形式去访问数据库，数据库表里面的字段就相当于对象的属性了。
可以直接obj.data的形式访问了。

# 使用步骤
# 1 下载Jar包 http://search.maven.org/
- 三个Jar包
- freemarker-2.3.22.jar //用java写的模板引擎，它能够基于模板来生成文本输出。应该就是用来自动生成DAO文件的
- greendao-generator-1.3.1.jar //生成Dao文件
- greendao-1.3.7.jar //Android开发中需要用到的

# 2 创建generator工程 见GreenDaoJava
- 创建三个表 生成 12个文件

public class ExampleDaoGenerator {

	public static void main(String[] args) throws Exception {
		// 参数3是数据库版本号，“com.cn.speedchat.greendao”是包名，也就是说生成的Dao文件会在这个包下，可以将Schema理解为数据库上下文吧
		Schema schema = new Schema(3, "com.cn.speedchat.greendao");
		// addNote() addSession() addReplay()这三个函数相当于建立了三个表，表名你都可以不用管了
		addNote(schema);
		addSession(schema);
		addReplay(schema);
		addCustomerOrder(schema);
		// 这个是生成Dao文件的路径的位置，这个代表当前工程的上一级目录的javagreendao的src-gen文件夹里面，其实就是跟src同一级目录，所以你自己要在src同一级目录下新建一个src-gen文件夹
		new DaoGenerator().generateAll(schema, "../GreenDaoJava/src-gen"); 
	}

	// 这个是一个Note表,然后后面的node.add***是表的字段名以及属性
	private static void addNote(Schema schema) {
		// "MqttChatEntity"相当于是表的类名，用MqttChatEntity生成对象就可以访问这个表属性了，也就是这个表对应了这个类，待会使用你就会明白了
		Entity note = schema.addEntity("MqttChatEntity");
		note.addIdProperty().autoincrement();
		note.addIntProperty("mode").notNull();
		note.addStringProperty("sessionid").notNull();
		note.addStringProperty("from").notNull();
		note.addStringProperty("to").notNull();
		note.addStringProperty("v_code");
		note.addStringProperty("timestamp").notNull();
		note.addStringProperty("platform");
		note.addStringProperty("message");
		note.addBooleanProperty("isread").notNull();
		note.addLongProperty("gossipid");
		note.addStringProperty("gossip");
		note.addIntProperty("chattype").notNull();
		note.addStringProperty("imagepath");
		note.addStringProperty("base64image");
	}

	// 这个是一个Session表,然后后面的node.add***是表的字段名以及属性（这是我写的会话的一个表）
	private static void addSession(Schema schema) {
		Entity note = schema.addEntity("SessionEntity");
		note.addIdProperty().autoincrement();
		note.addStringProperty("sessionid").notNull().unique();
		note.addStringProperty("from").notNull();
		note.addStringProperty("to").notNull();
		note.addLongProperty("gossipid").notNull();
		note.addStringProperty("gossip");
		note.addIntProperty("sessiontype").notNull();
		note.addBooleanProperty("asdasd").notNull();
	}

	// 这个是一个Replay表,然后后面的node.add***是表的字段名以及属性（这是我写的回复的一个表）
	private static void addReplay(Schema schema) {
		// ReplayEntity对应的类名
		Entity note = schema.addEntity("ReplayEntity");
		note.addIdProperty().autoincrement();
		note.addIntProperty("mode").notNull();
		note.addStringProperty("from").notNull();
		note.addStringProperty("to").notNull();
		note.addStringProperty("v_code");
		note.addStringProperty("timestamp").notNull();
		note.addStringProperty("platform");
		note.addStringProperty("message");
		note.addIntProperty("msgtype").notNull();
		note.addBooleanProperty("isread").notNull();
	}

	// 这个不用管了，你照抄吧
	private static void addCustomerOrder(Schema schema) {
		Entity customer = schema.addEntity("Customer");
		customer.addIdProperty();
		customer.addStringProperty("name").notNull();
		Entity order = schema.addEntity("Order");
		order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
		order.addIdProperty();
		Property orderDate = order.addDateProperty("date").getProperty();
		Property customerId = order.addLongProperty("customerId").notNull().getProperty();
		order.addToOne(customer, customerId);
		ToMany customerToOrders = customer.addToMany(order, customerId);
		customerToOrders.setName("orders");
		customerToOrders.orderAsc(orderDate);
	}
}


# 3 创建Android工程 GreenDaoTest
- MainActivity

	private DBHelper dBManager; // 定义一个DBHelper对象，用他来对数据库进行增删改查

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dBManager = DBHelper.getInstance(this); // 得到DBHelper对象
		SessionEntity entity = new SessionEntity(); // 创建一个SessionEntity实体对象，并赋值
		entity.setFrom("A");
		entity.setGossip("大家好吗？我来了...");
		entity.setGossipid(10);
		entity.setSessionid("abcdefg");
		entity.setSessiontype(1);
		entity.setTo("B");
		// 下面这一行就把entity对象存数据库了，然后我们新建一个SessionEntity列表再读一下
		dBManager.saveSession(entity); // 保存到数据库
		// 下面这个方法是查询Session表里面的所有数据返回一个数据列表，相当于select * from table，然后扫描打印出来
		List<SessionEntity> listentity = dBManager.loadAllSession();
		for (int i = 0; i < listentity.size(); i++) {
			SessionEntity tmpEntity = listentity.get(i);
			Log.v("tmpEntity.getFrom()", tmpEntity.getFrom());
			Log.v("tmpEntity.getGossip()", tmpEntity.getGossip());
			Log.v("tmpEntity.getGossipid()", tmpEntity.getGossipid() + "");
			Log.v("tmpEntity.getSessionid()", tmpEntity.getSessionid());
			Log.v("tmpEntity.getSessiontype()", tmpEntity.getSessiontype() + "");
			Log.v("tmpEntity.getTo()", tmpEntity.getTo());
		}
	}

- DBHelper

public class DBHelper {

	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static Context appContext;
	private DaoSession mDaoSession;
	private MqttChatEntityDao chatDao;
	private SessionEntityDao sessionDao;

	private DBHelper() {
	}

	// 单例模式，DBHelper只初始化一次
	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (appContext == null) {
				appContext = context.getApplicationContext();
			}
			instance.mDaoSession = ControlApp.getDaoSession(context);
			instance.chatDao = instance.mDaoSession.getMqttChatEntityDao();
			instance.sessionDao = instance.mDaoSession.getSessionEntityDao();
		}
		return instance;
	}

	// 删除Session表
	public void dropSessionTable() {
		SessionEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 删除MqttChatEntity表
	public void dropChatTable() {
		MqttChatEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 删除所有表
	public void dropAllTable() {
		MqttChatEntityDao.dropTable(mDaoSession.getDatabase(), true);
		SessionEntityDao.dropTable(mDaoSession.getDatabase(), true);
		ReplayEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 创建所有表
	public void createAllTable() {
		MqttChatEntityDao.createTable(mDaoSession.getDatabase(), true);
		SessionEntityDao.createTable(mDaoSession.getDatabase(), true);
		ReplayEntityDao.createTable(mDaoSession.getDatabase(), true);
	}

	/**
	 * insert or update note
	 * 
	 * @param note
	 * @return insert or update note id
	 */
	// 插入或者删除session项
	public long saveSession(SessionEntity session) {
		return sessionDao.insertOrReplace(session);
	}

	// 获得所有的Session倒序排存到List列表里面
	public List<SessionEntity> loadAllSession() {
		List<SessionEntity> sessions = new ArrayList<SessionEntity>();
		List<SessionEntity> tmpSessions = sessionDao.loadAll();
		int len = tmpSessions.size();
		for (int i = len - 1; i >= 0; i--) {
			sessions.add(tmpSessions.get(i));
		}
		return sessions;
	}

	public void DeleteSession(SessionEntity entity) {
		sessionDao.delete(entity);
	}

	// 删除某一项Session
	public void DeleteNoteBySession(SessionEntity entity) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Sessionid.eq(entity.getSessionid()));
		List<MqttChatEntity> chatEntityList = mqBuilder.build().list();
		chatDao.deleteInTx(chatEntityList);
	}

	// 根据id找到某一项
	public MqttChatEntity loadNote(long id) {
		return chatDao.load(id);
	}

	// 获得所有的MqttChatEntity列表
	public List<MqttChatEntity> loadAllNote() {
		return chatDao.loadAll();
	}

	/**
	 * query list with where clause ex: begin_date_time >= ? AND end_date_time
	 * <= ?
	 * 
	 * @param where
	 *            where clause, include 'where' word
	 * @param params
	 *            query parameters
	 * @return
	 */
	// 查询满足params条件的列表
	public List<MqttChatEntity> queryNote(String where, String... params) {
		ArrayList<MqttChatEntity> ad = new ArrayList<MqttChatEntity>();
		return chatDao.queryRaw(where, params);
	}

	// 不一一介绍了，大家可以自己写，有些比较难的查询可以使用QueryBuilder来查询
	public List<MqttChatEntity> loadLastMsgBySessionid(String sessionid) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Sessionid.eq(sessionid)).orderDesc(Properties.Id).limit(1);
		return mqBuilder.list();
	}

	public List<MqttChatEntity> loadMoreMsgById(String sessionid, Long id) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Id.lt(id)).where(Properties.Sessionid.eq(sessionid)).orderDesc(Properties.Id)
				.limit(20);
		return mqBuilder.list();
	}

	/**
	 * delete all note
	 */
	public void deleteAllNote() {
		chatDao.deleteAll();
	}

	/**
	 * delete note by id
	 * 
	 * @param id
	 */
	public void deleteNote(long id) {
		chatDao.deleteByKey(id);
		Log.i(TAG, "delete");
	}

	public void deleteNote(MqttChatEntity note) {
		chatDao.delete(note);
	}
}

ControlApp

public class ControlApp extends Application {

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	public static SQLiteDatabase db;
	public static final String DB_NAME = "dbname.db"; // 数据库名，表名是自动被创建的

	/**
	 * 取得DaoMaster
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	/**
	 * 取得DaoSession
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}

	/**
	 * 得到Datebase
	 * 
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase getSQLDatebase(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			db = daoMaster.getDatabase();
		}
		return db;
	}

	@Override
	public void onCreate() {
	}
}



