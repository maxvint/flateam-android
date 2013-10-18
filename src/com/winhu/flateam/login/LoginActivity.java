package com.winhu.flateam.login;

import com.yuwenhui.flateam.R;
import com.winhu.flateam.home.HomeActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements TextWatcher {
	
	private AutoCompleteTextView email;
	private AutoTextViewAdapter adapter;
	private static final String[] AUTO_EMAILS = {"@163.com", "@sina.com", "@qq.com", "@126.com", "@gmail.com", "@apple.com"};
	private static EditText password;
	private static Button loginbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		email = (AutoCompleteTextView) findViewById(R.id.email);
		adapter = new AutoTextViewAdapter(this);
		email.setAdapter(adapter);
		email.setThreshold(1);//输入一个字符开始检测
		email.addTextChangedListener(this);//监听autoview的变化
		
		password = (EditText) this.findViewById(R.id.password);
		loginbtn = (Button) this.findViewById(R.id.loginbtn);
		
		
		loginbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putString("email", email.getText().toString());
				data.putString("password", password.getText().toString());
				
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, HomeActivity.class);
				startActivity(intent);  
                finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String input = s.toString();  
        adapter.mList.clear();  
        autoAddEmails(input);
        adapter.notifyDataSetChanged();  
        email.showDropDown();  
	}
	
	/*
	 * 自动填充邮箱列表
	 * @param input
	 */
	private void autoAddEmails(String input) {
		System.out.println("input-->" + input);
		String autoEmail = "";
        if(input.length() > 0) {
            for(int i = 0; i < AUTO_EMAILS.length; ++i) {
            	if(input.contains("@")) {//包含“@”则开始过滤
            		String filter = input.substring(input.indexOf("@") + 1, input.length());//获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱
            		System.out.println("filter-->" + filter);
            		if(AUTO_EMAILS[i].contains(filter)) {//符合过滤条件
            			autoEmail = input.substring(0, input.indexOf("@")) + AUTO_EMAILS[i];//用户输入“@”之前的内容加上自动填充的内容即为最后的结果
            			adapter.mList.add(autoEmail);
            		}
            	} else {
            		autoEmail = input + AUTO_EMAILS[i];
            		adapter.mList.add(autoEmail);
            	}
            }
        }
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
