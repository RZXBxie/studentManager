package com.student_manager;

import java.util.ArrayList;

public class ArrayListDemo {
	public static void main(String[] args) {
		/*
		boolean add(E e) 添加

		boolean remove(E e) 删除
		E remove(int index)

		E set(int index, E e) 修改

		E get(int index) 查询

		int size() 获取长度

		 */

		ArrayList<String> listString = new ArrayList<>();
		ArrayList<Integer> listInt = new ArrayList<>();
		ArrayList<Student> listStudent = new ArrayList<>();
		listStudent.add(new Student("001", "zhang san", 20, "whu"));

		System.out.println(listStudent);
		System.out.println(listString);
	}
}
