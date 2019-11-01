package com.cold.util;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;

public class CorrespondUtil {

	/**
	 * 预处理对应关系
	 * 
	 * @param correspond
	 */

	public static Map<Integer, Collection<Integer>> PretreatmentCorrespond(String correspond) {
		Multimap<Integer, Integer> map = TreeMultimap.create();
		String[] splits = StringUtils.split(correspond);
		for (String string : splits) {
			if (StringUtils.isBlank(string))
				continue;
			String[] postions = StringUtils.split(string, "-");
			map.put(Integer.parseInt(postions[0]), Integer.parseInt(postions[1]));
			//add(map,Integer.parseInt(postions[0]), Integer.parseInt(postions[1]));
		}
		return map.asMap();
	}

	private static void add(Map<Integer, ArrayList<Integer>> map,int enPos, int cnPos) {
		ArrayList<Integer> t = null;
		if (!map.containsKey(enPos)) {
			t = new ArrayList<>();
			map.put(enPos, t);
		}
		t = map.get(enPos);
		if(t==null)t = new ArrayList<>();
		t.add(cnPos);
		//Collections.sort(t);
	}

	public static void main(String[] args) {
		String correspond = "0-0 1-1 2-2 3-3 4-4 5-5 5-6 6-6 12-7 13-8 14-9 8-10 9-10 11-10 10-11 7-12 15-13 16-14 18-15 17-16 18-17 19-18";
		correspond = "0-0 1-1 2-2 3-2 3-8 7-3 3-3 8-4 9-4 15-5 4-6 5-7 6-8 10-9 11-10 12-10 13-11 14-12 16-13 17-14 18-15 19-15 20-16 21-17 3-1";
        correspond = "0-0 1-1 2-2 3-3 4-4 5-5 6-6 7-6 8-7 19-8 21-9 21-10 22-10 23-11 16-12 17-13 18-14 10-15 11-15 12-16 14-17 14-18 15-18 24-19 26-20 27-20 28-21 29-22 30-23 31-23";
		Map<Integer, Collection<Integer>> map = PretreatmentCorrespond(correspond);
		for (Map.Entry<Integer, Collection<Integer>> entry : map.entrySet()) {
			String s = MessageFormat.format("enPos:{0},cnPos:{1}",
					entry.getKey(), entry.getValue());
			System.out.println(s + ">>");
		}
	}
}
