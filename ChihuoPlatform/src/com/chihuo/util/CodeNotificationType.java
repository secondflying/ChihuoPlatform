package com.chihuo.util;

public enum CodeNotificationType {
		AddMenu(10, "加减菜"), AlterMenu(11, "改菜状态"), RequestCheckOut(12, "请求结账"), CheckOut(
				13, "结账"), CheckIn(14, "撤单"),

		AddWater(21, "加水"), AddDish(22, "加餐具"), CallWaiter(23, "叫服务员");

		private final int type;
		private final String description;

		CodeNotificationType(int type, String des) {
			this.type = type;
			this.description = des;
		}

		public String toString() {
			return type + "";
		}

		public static CodeNotificationType fromInteger(int x) {
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