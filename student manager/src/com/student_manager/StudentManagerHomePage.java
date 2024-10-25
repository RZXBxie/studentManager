package com.student_manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class StudentManagerHomePage {
	static final HashSet<String> uniqueUsername = new HashSet<>();
	static final HashMap<String, User> userMap = new HashMap<>();
	static final int MAX_RETRY_COUNT = 3;

	public static void homePage() {
		while (true) {
			System.out.println("欢迎来到学生管理系统");
			System.out.println("请选择操作：1.登录 2.注册 3.找回密码 4.退出");
			Scanner sc = new Scanner(System.in);
			String choice = sc.next();
			switch (choice) {
				case "1":
					login();
					break;
				case "2":
					register();
					break;
				case "3":
					forgetPassword();
					break;
				case "4":
					System.exit(0);
				default:
					System.out.println("输入有误，请重新输入");
					break;
			}

		}
	}

	/**
	 * <p>输入用户名、密码、身份证号码和手机号码。</p>
	 *
	 * <p>1. 用户名需要满足如下全部要求：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(1) 用户名唯一</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(2) 用户名长度必须在3-15位之间</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(3) 只能是字母+数字的组合，但不能是纯数字</p>
	 *
	 * <p>2. 密码键盘输入两次，两次一致注册才算成功</p>
	 *
	 * <p>3. 身份证号码需要验证：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(1) 长度为18位</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(2) 不能以0开头</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(3) 前17位，必须都是数字</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(4) 最后一位可以是数字，也可以是大写X或小写x</p>
	 *
	 * <p>4. 手机号验证：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(1) 长度为11位</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(2) 不能以0开头</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;(3) 必须都是数字</p>
	 */
	public static void register() {
		Scanner sc = new Scanner(System.in);
		String username, password, id, phoneNumber;
		while (true) {
			System.out.println("请输入用户名：");
			username = sc.next();
			if (!checkUsername(username)) {
				System.out.println("用户名格式错误，请重新输入！");
			} else {
				break;
			}
		}
		while (true) {
			System.out.println("请输入密码：");
			String password1 = sc.next();
			System.out.println("请确认密码：");
			String password2 = sc.next();
			if (!password1.equals(password2)) {
				System.out.println("两次密码输入不一致，请重新输入！");
			} else {
				password = password1;
				break;
			}
		}
		while (true) {
			System.out.println("请输入身份证号码：");
			id = sc.next();
			if (!checkID(id)) {
				System.out.println("身份证号格式错误，请重新输入！");
			} else {
				break;
			}
		}
		while (true) {
			System.out.println("请输入手机号码：");
			phoneNumber = sc.next();
			if (!checkPhoneNumber(phoneNumber)) {
				System.out.println("手机号码格式错误，请重新输入！");
			} else {
				break;
			}
		}

		User user = new User(username, password, id, phoneNumber);
		userMap.put(username, user);
		System.out.println("注册成功！");
	}

	/**
	 * <p>键盘录入用户名、密码和验证码。</p>
	 *
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;1. 如果用户未注册，提示注册后再登录。</p>
	 *
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;2. 判断验证码是否正确，如果不正确，重新输入。</p>
	 *
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;3. 再判断用户名和密码是否正确，有三次机会。</p>
	 */
	public static void login() {
		Scanner sc = new Scanner(System.in);
		String username, password, verifyCode;
		while (true) {
			System.out.println("请输入验证码：");
			verifyCode = sc.next();
			if (!checkVerifyCode(verifyCode)) {
				System.out.println("验证码错误，请重新输入！");
			} else {
				break;
			}
		}
		System.out.println("请输入用户名：");
		username = sc.next();
		if (!uniqueUsername.contains(username)) {
			System.out.println("用户名不存在，请注册后重新输入");
			return;
		}
		for (int i = 0; i < MAX_RETRY_COUNT; i++) {
			System.out.println("请输入密码：");
			password = sc.next();
			if (!password.equals(userMap.get(username).getPassword())) {
				if (i != MAX_RETRY_COUNT - 1) {
					System.out.printf("密码错误，您还剩%d次机会\n", MAX_RETRY_COUNT - 1 - i);
				} else {
					System.out.println("您的账号已被锁定，请点击忘记密码进行找回！");
					return;
				}
			} else {
				break;
			}
		}
		System.out.println("登录成功！");
		StudentManager.manageStudents();
	}

	/**
	 * <p>1. 键盘录入用户名，判断用户是否存在，如不存在，直接结束，并提示：“用户不存在”</p>
	 *
	 * <p>2. 键盘录入身份证号码和手机号码</p>
	 *
	 * <p>3. 判断当前用户的身份证号码和手机号码是否一致，如果一致，则提示输入密码进行修改；否则提示账号信息不匹配，修改失败</p>
	 */
	public static void forgetPassword() {
		Scanner sc = new Scanner(System.in);
		String username, id, phoneNumber, password;
		System.out.println("请输入用户名：");
		username = sc.next();
		if (!uniqueUsername.contains(username)) {
			System.out.println("当前用户名不存在");
			return;
		}
		System.out.println("请输入身份证号：");
		id = sc.next();
		if (!(id.equals(userMap.get(username).getPhoneNumber()))) {
			System.out.println("账号信息不匹配，修改失败！");
			return;
		}
		System.out.println("请输入手机号：");
		phoneNumber = sc.next();
		if (!(phoneNumber.equals(userMap.get(username).getId()))) {
			System.out.println("账号信息不匹配，修改失败！");
			return;
		}
		System.out.println("请输入新密码：");
		password = sc.next();
		userMap.get(username).setPassword(password);
		System.out.println("修改密码成功！");
	}

	/**
	 * <p>用户名规则：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;1. 用户名唯一</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;2. 用户名长度必须在3-15位之间</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;3. 只能是字母+数字的组合，但不能是纯数字</p>
	 */
	static boolean checkUsername(String username) {
		if (username.length() < 3 || username.length() > 15) {
			return false;
		}
		if (uniqueUsername.contains(username)) {
			return false;
		}
		uniqueUsername.add(username);
		int numberCount = 0;
		for (int i = 0; i < username.length(); i++) {
			char c = username.charAt(i);
			if (c <= '9') numberCount++;
		}

		return numberCount != username.length() && username.matches("^[a-zA-Z0-9]*$");
	}

	/**
	 * <p>身份证号码规则：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;1. 长度为18位</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;2. 不能以0开头</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;3. 前17位，必须都是数字</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;4. 最后一位可以是数字，也可以是大写X或小写x</p>
	 */
	static boolean checkID(String id) {
		if (id.length() != 18) return false;
		if (id.charAt(0) == '0') return false;
		for (int i = 0; i < id.length() - 1; i++) {
			char c = id.charAt(i);
			if (c < '0' || c > '9') return false;
		}
		char c = id.charAt(id.length() - 1);
		return (c >= '0' && c <= '9' || c == 'X' || c == 'x');
	}

	/**
	 * <p>手机号规则：</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;1. 长度为11位</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;2. 不能以0开头</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;3. 必须都是数字</p>
	 *
	 * @param phoneNumber 输入的手机号
	 * @return 手机号是否合法
	 */
	static boolean checkPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() != 11) return false;
		if (phoneNumber.charAt(0) == '0') return false;
		for (int i =  0; i < phoneNumber.length(); i++) {
			char c = phoneNumber.charAt(i);
			if (c < '0' || c > '9') return false;
		}
		return true;
	}

	/**
	 * <p>验证码规则：</p>
	 *
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;1. 长度为5。</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;2. 由四位大写或者小写字母和一位数字组成。</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;3. 数字可以出现在任意位置。</p>
	 *
	 * @param verifyCode 输入的验证码
	 * @return 验证码是否合法
	 */
	static boolean checkVerifyCode(String verifyCode) {
		if (verifyCode.length() != 5) return false;
		return verifyCode.matches("^[a-zA-Z]*[0-9][a-zA-Z]*$");
	}
}
