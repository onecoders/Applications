package net.learn2develop.DialogFragmentExample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Fragment1 extends DialogFragment {

	static Fragment1 newInstance(String title) {
		Fragment1 fragment = new Fragment1();
		Bundle args = new Bundle();
		args.putString("title", title);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		return new AlertDialog.Builder(getActivity())
		.setIcon(R.drawable.ic_launcher)
		.setTitle(title)
		.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, 
					int whichButton) {
				((DialogFragmentExampleActivity)
						getActivity()).doPositiveClick();
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, 
					int whichButton) {
				((DialogFragmentExampleActivity)
						getActivity()).doNegativeClick();
			}
		}).create();
	}        

}
