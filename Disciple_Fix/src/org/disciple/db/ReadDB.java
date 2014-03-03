package org.disciple.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.disciple.model.McheyneVo;

public class ReadDB {
	/**
	 * 테스트를 위한 놈!
	 */
	public List<McheyneVo> selectMcheyne(Context context) {
		Abatis abatis = new Abatis(context);
		return abatis.executeForBeanList("selectMcheyneByDate", null, McheyneVo.class);
	}
	
	public List<McheyneVo> selectMcheyneByMonth(Context context, int month) {
		Abatis abatis = new Abatis(context);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("month", month);
		return abatis.executeForBeanList("selectMcheyneByMonth", param, McheyneVo.class);
	}
	
	public McheyneVo selectOneMcheyne(Context context) {
		Abatis abatis = new Abatis(context);
		return abatis.executeForBean("selectOneMcheyne", null, McheyneVo.class);
	}
}
