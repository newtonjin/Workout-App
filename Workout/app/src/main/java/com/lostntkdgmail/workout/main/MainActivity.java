package com.lostntkdgmail.workout.main;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.data_entry.LiftSelection;
import com.lostntkdgmail.workout.data_entry.TypeSelection;

import com.lostntkdgmail.workout.data_entry.WeightSelection;
import com.lostntkdgmail.workout.database.LiftTableAccessor;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.database.WeightTableAccessor;
import com.lostntkdgmail.workout.users.EditUser;
import com.lostntkdgmail.workout.users.SelectUser;
import com.lostntkdgmail.workout.view.CalendarFragment;
import com.lostntkdgmail.workout.view.ViewHistoryFragment;


import static android.app.AlertDialog.*;

/**
 * The Main Activity of the App
 */
public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private PagerAdapter pagerAdapter;
    private NonSwipeViewPager viewPager;

    public static String TYPE, LIFT;
    public static long USER;
    public static int currentPos = 0;
    public static LiftTableAccessor liftTable;
    public static UserTableAccessor userTable;
    public static WeightTableAccessor weightTable;

    /**
     * Determines what happens when the Activity is created. Sets up the menus, initializes the database accessors and various variables.
     * @param savedInstanceState The saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liftTable = new LiftTableAccessor(this);
        userTable = new UserTableAccessor(this);
        weightTable = new WeightTableAccessor(this);
        TYPE = liftTable.getTypes()[0];
        LIFT = liftTable.getLifts(TYPE)[0];
        USER = Long.parseLong(userTable.getAllIds()[0]);
        setContentView(R.layout.activity_main);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);

        setUpViewPager();

        setViewPager(TypeSelection.TITLE);

        //Setting up navigation
        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.recordWeightsNav:
                        currentPos++;
                        setViewPager(TypeSelection.TITLE);
                        break;
                    case R.id.switchUserNav:
                        setViewPager(SelectUser.TITLE);
                        break;
                    case R.id.pastEntriesNav:
                        setViewPager(CalendarFragment.TITLE);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpViewPager() {
        pagerAdapter.addFragment(new CalendarFragment(), CalendarFragment.TITLE);
        pagerAdapter.addFragment(new ViewHistoryFragment(), ViewHistoryFragment.TITLE);

        pagerAdapter.addFragment(new TypeSelection(), TypeSelection.TITLE);

        //TODO: we could init the other fragments in other threads to speed up?
        pagerAdapter.addFragment(new LiftSelection(), LiftSelection.TITLE);
        pagerAdapter.addFragment(new WeightSelection(), WeightSelection.TITLE);
        pagerAdapter.addFragment(new SelectUser(), SelectUser.TITLE);

        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * Sets the view pager to the given page
     * @param fragmentNum The index of the page
     */
    public void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    /**
     * Sets the view pager to the given page
     * @param title The title of the page
     */
    public void setViewPager(String title) {
        int index = pagerAdapter.getFragmentIndex(title);
        setViewPager(index);
    }

    /**
     * Returns the instance of the pager adapter
     * @return The instance of the pager adapter
     */
    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    /**
     * Determines what happens when the back button is pressed
     */
    @Override
    public void onBackPressed() { //TODO: Once again needs to be looked at
        int selectUserIndex = pagerAdapter.getFragmentIndex(SelectUser.TITLE);
        int startIndex = pagerAdapter.getFragmentIndex(TypeSelection.TITLE);
        int currentIndex = viewPager.getCurrentItem();
        if (currentIndex > 0 && currentIndex < selectUserIndex) {
            viewPager.setCurrentItem(startIndex + --currentPos);
        }
        else if(selectUserIndex == currentIndex) {
            setViewPager(startIndex + currentPos);
        }
        else if(currentIndex > selectUserIndex) {
            viewPager.setCurrentItem(selectUserIndex);
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * Determines what happens when the Activity is destroyed. Closes the database accessors
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        liftTable.close();
        weightTable.close();
        userTable.close();
    }

    /**
     * In the Select User page, this is what is called when the edit button is pressed. Opens the edit user page
     * @param button The edit button
     */
    public void onEditUserClick(View button) {
        View parentRow = (View)button.getParent();
        ListView listView = (ListView)parentRow.getParent();
        int position = listView.getPositionForView(parentRow);
        EditUser.userId = Long.parseLong(SelectUser.ids[position]);
        if(!SelectUser.editInitialized) {
            addFragment(new EditUser(), EditUser.TITLE);
            SelectUser.editInitialized = true;
        }
        setViewPager(EditUser.TITLE);
    }

    /**
     * In the Select User page, this is what is called when the delete button is pressed. Deletes the selected user
     * @param button The delete button
     */
    public void onDeleteUserClick(View button) {
        View parentRow = (View)button.getParent();
        ListView listView = (ListView)parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final String name = SelectUser.getUsers()[position];

        Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new Builder(this);
        }
        if(SelectUser.ids.length == 1) {
            //TODO: Show error message: There must be at least 1 user
            return;
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete \""+name+"\" and all workouts associated with that user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userTable.deleteData(SelectUser.ids[position]);
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SelectUser.TITLE);
                        System.out.println(fragment == null);
                        ((SelectUser)PagerAdapter.fragmentList.get(pagerAdapter.getFragmentIndex(SelectUser.TITLE))).reload();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Adds a fragment to the pagerAdapter
     * @param fragment The Fragment to add
     * @param title The title of the Fragment
     */
    public void addFragment(Fragment fragment, String title) {
        pagerAdapter.addFragment(fragment, title);
    }

    /**
     * Called from the Calendar Fragment when the user clicks on a date to view
     * @param date The selected date
     */
    public void updateViewHistory(String date){
        ((ViewHistoryFragment)pagerAdapter.getItem(pagerAdapter.getFragmentIndex(ViewHistoryFragment.TITLE))).initList(date);
    }
}
