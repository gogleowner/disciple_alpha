package com.disciple.activity;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.disciple.model.McheyneVo;
import com.disciple.presenter.MchyneBtnClick;

public class Tab4_McheyneAdapter  extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	List<McheyneVo> listVo;
	int layout;
	public Tab4_McheyneAdapter(Context context, int layout, List<McheyneVo> listVo) {
		super();
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listVo = listVo;
		this.layout = layout;
		Log.d("listVo value???", "adsfasdf "+ listVo.size());
	}

	@Override
	public int getCount() {
		return listVo.size();
	}

	@Override
	public Object getItem(int position) {
		return listVo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		TextView tv;
		Button bt[];
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		if(convertView == null) {
			convertView = inflater.inflate(layout, parent, false);
		}
		
		ViewHolder holder = new ViewHolder();
		holder.bt = new Button[4];
		holder.tv = (TextView) convertView.findViewById(R.id.dayTV);
		holder.bt[0] = (Button) convertView.findViewById(R.id.button1);
		holder.bt[1] = (Button) convertView.findViewById(R.id.button2);
		holder.bt[2] = (Button) convertView.findViewById(R.id.button3);
		holder.bt[3] = (Button) convertView.findViewById(R.id.button4);
		
		holder.tv.setText(String.valueOf(listVo.get(pos).getDay()));
		holder.bt[0].setText(listVo.get(pos).getWord1());
		holder.bt[1].setText(listVo.get(pos).getWord2());
		holder.bt[2].setText(listVo.get(pos).getWord3());
		holder.bt[3].setText(listVo.get(pos).getWord4());
		
		holder.bt[0].setOnClickListener(new MchyneBtnClick(holder.bt[0], listVo.get(pos).getMonth(), listVo.get(pos).getDay()));
		holder.bt[1].setOnClickListener(new MchyneBtnClick( holder.bt[1], listVo.get(pos).getMonth(), listVo.get(pos).getDay()));
		holder.bt[2].setOnClickListener(new MchyneBtnClick(holder.bt[2], listVo.get(pos).getMonth(), listVo.get(pos).getDay()));
		holder.bt[3].setOnClickListener(new MchyneBtnClick(holder.bt[3], listVo.get(pos).getMonth(), listVo.get(pos).getDay()));
		return convertView;
	}
	
	
}