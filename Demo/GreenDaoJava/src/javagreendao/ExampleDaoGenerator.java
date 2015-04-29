package javagreendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

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
