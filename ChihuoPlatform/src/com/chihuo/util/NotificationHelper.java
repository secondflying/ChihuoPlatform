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
			CodeNotificationType ntype, Device device) {
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
			CodeNotificationType ntype, Device device) {
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
}