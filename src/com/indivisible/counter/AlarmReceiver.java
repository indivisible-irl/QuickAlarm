package com.indivisible.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String ALARM_ID = "alarmId";
		context.startActivity(new Intent(context, AlarmDisp.class)
        	.putExtra(AlarmDisp.ALARM_ID, intent.getExtras().getInt(ALARM_ID))
        	.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));  
	}

}
