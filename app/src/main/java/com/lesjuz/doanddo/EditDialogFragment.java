package com.lesjuz.doanddo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Lesjuz on 2/28/2017.
 */

public class EditDialogFragment extends DialogFragment {



    public interface EditInterface {
        void updateTask(String inputText,int id);
    }

    public EditDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditDialogFragment newInstance(int id,String task) {
        EditDialogFragment frag = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("task", task);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context ctx = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_edit, null, false);
        final EditText editText1 = (EditText) rootView.findViewById(R.id.task_name);
        final int DbId = getArguments().getInt("id");
        final String task_for_edit = getArguments().getString("task");
        editText1.setText(task_for_edit);

        return new AlertDialog.Builder(ctx)
                .setTitle("Edit")
                .setView(rootView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText1.setError(null);

                        String title = editText1.getText().toString();
                        if (TextUtils.isEmpty(title)) {
                            editText1.setError("Can't be empty");
                            return;
                        }



                            EditInterface activity = (EditInterface) getActivity();
                            activity.updateTask(editText1.getText().toString(),DbId);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_edit, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Edit");
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.task_name);
        // Fetch arguments from bundle and set title


        //dismiss();
        //


    }
*/


}



