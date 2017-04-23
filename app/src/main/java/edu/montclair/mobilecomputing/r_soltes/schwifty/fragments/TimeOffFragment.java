package edu.montclair.mobilecomputing.r_soltes.schwifty.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.montclair.mobilecomputing.r_soltes.schwifty.R;


public class TimeOffFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
<<<<<<< HEAD
=======
=======
<<<<<<< HEAD
public class TimeOffFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
=======
public class TimeOffFragment extends Fragment {
>>>>>>> origin/UI-branch
>>>>>>> origin/UI-branch
>>>>>>> origin/UI-branch

    public TimeOffFragment() {
        // Empty Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_off, container, false);


        //lines of code below creates the dropdown to select your major

        Spinner s = (Spinner) view.findViewById(R.id.timeoff_spinner);
<<<<<<< HEAD

=======
=======
<<<<<<< HEAD
        Spinner s = (Spinner) view.findViewById(R.id.timeoff_spinner);
=======
        Spinner s = (Spinner) View.findViewById(R.id.timeoff_spinner);
>>>>>>> origin/UI-branch
>>>>>>> origin/UI-branch
>>>>>>> origin/UI-branch
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        return view;
    }




    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) getView().findViewById(R.id.picDate)).setText(dateFormat.format(calendar.getTime()));

    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }
    }




}






