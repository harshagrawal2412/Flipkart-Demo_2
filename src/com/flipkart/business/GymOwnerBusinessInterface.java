package com.flipkart.business;

import com.flipkart.bean.*;
import java.util.*;

public interface GymOwnerBusinessInterface{
    public GymOwner getProfile(String email);
    /*
  returns the gym owner's profile
    */
    public void editProfile(GymOwner gymOwnerNews);
    /*
allows the gym owner to edit profile
     */

    public boolean addGym(Gym gym);
    /*
allows the gym owner to add new gym
     */

    public void editGym(Gym gym);
    /*
allows the gym owner to edit the gym information
     */

    public List<Gym> getGymDetail(String gymOwnerEmail);
    /*
returns the list of all gyms owned by the gym owner
     */

    public boolean isApproved(String email);
    /*
  returns true if the gym owner is approved else returns false
     */

    public boolean isGymApproved(String gymId);
    /*
  returns true if the gym  is approved else returns false
     */
}