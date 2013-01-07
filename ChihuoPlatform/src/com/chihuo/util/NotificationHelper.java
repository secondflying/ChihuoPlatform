package com.chihuo.util;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Device;

public class NotificationHelper {

	public static void sendNotifcationToWaiter(String message,
			NotificationType ntype, Device device) {
		if (device != null) {
			JPushClient jpush = new JPushClient(PublicConfig.getJWaiterName(),
					PublicConfig.getJWaiterPassword(),
					PublicConfig.getJWaiterAppKey());
			int sendNo = 1;
			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo, device.getDeviceid(), ntype.toString(), message);
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
			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo, device.getDeviceid(), ntype.toString(), message);
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
	}

}