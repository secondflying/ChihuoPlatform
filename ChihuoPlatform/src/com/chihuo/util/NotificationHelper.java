package com.chihuo.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Device;

public class NotificationHelper {

	public static void sendNotificationToWaiter(String message,
			String title,Integer oid, Device device) {
		if (device != null) {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("oid", oid);
			
			JPushClient jpush = new JPushClient(PublicConfig.getJWaiterName(),
					PublicConfig.getJWaiterPassword(),
					PublicConfig.getJWaiterAppKey());
			int sendNo = 1;
			MessageResult msgResult = jpush.sendNotificationWithImei(sendNo, device.getDeviceid(), title, message, 1, extra);
			if (null != msgResult) {
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
				} else {
					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
							+ ", 错误消息=" + msgResult.getErrmsg());
				}
			} else {

				System.out.println("无法获取数据");
			}
		}
	}
	
	public static void sendMessageToWaiter(String message,
			NotificationType ntype, Device device) {
		if (device != null) {
			JPushClient jpush = new JPushClient(PublicConfig.getJWaiterName(),
					PublicConfig.getJWaiterPassword(),
					PublicConfig.getJWaiterAppKey());
			int sendNo = 1;
			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo,
					device.getDeviceid(), ntype.toString(), message);
			if (null != msgResult) {
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
				} else {
					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
							+ ", 错误消息=" + msgResult.getErrmsg());
				}
			} else {

				System.out.println("无法获取数据");
			}
		}
	}

	public static void sendNotifcationToUser(String message,
			NotificationType ntype, Device device) {
		if (device != null) {
			JPushClient jpush = new JPushClient(PublicConfig.getJUserName(),
					PublicConfig.getJUserPassword(),
					PublicConfig.getJUserAppKey());
			int sendNo = 1;
			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo,
					device.getDeviceid(), ntype.toString(), message);
			if (null != msgResult) {
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
				} else {
					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
							+ ", 错误消息=" + msgResult.getErrmsg());
				}
			} else {
				System.out.println("无法获取数据");
			}
		}
	}

	public static String getDeskString(int oid, Desk desk) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("oid", oid);
			jsonObject.put("id", desk.getId());
			jsonObject.put("name", desk.getName());
		} catch (JSONException e) {
		}
		return jsonObject.toString();
	}

	public enum NotificationType {
		AddMenu(10, "加减菜"), AlterMenu(11, "改菜状态"), RequestCheckOut(12, "请求结账"), CheckOut(
				13, "结账"), CheckIn(14, "撤单"),

		AddWater(21, "加水"), AddDish(22, "加餐具"), CallWaiter(23, "叫服务员");

		private final int type;
		private final String description;

		NotificationType(int type, String des) {
			this.type = type;
			this.description = des;
		}

		public String toString() {
			return type + "";
		}

		public static NotificationType fromInteger(int x) {
			switch (x) {
			case 10:
				return AddMenu;
			case 11:
				return AlterMenu;
			case 12:
				return RequestCheckOut;
			case 13:
				return CheckOut;
			case 14:
				return CheckIn;
			case 21:
				return AddWater;
			case 22:
				return AddDish;
			case 23:
				return CallWaiter;
			}
			return null;
		}

	}

}