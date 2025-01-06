/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for(int i =0; i<userCount;i++){
            if(this.users[i]!=null && this.users[i].getName().equalsIgnoreCase(name)){
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
    for (int i = 0; i < userCount; i++) {
        if (this.users[i] != null && this.users[i].getName().equalsIgnoreCase(name)) {
            return false; 
        }
    }
    for (int i = 0; i < this.users.length; i++) {
        if (this.users[i] == null) {
            this.users[i] = new User(name); 
            userCount++; 
            return true;
        }
    }
    return false;
    }

    /** Makes the user with name1 follow the user with name2. If  successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null || name1.equalsIgnoreCase(name2)) {
            return false; 
        }
    
        User user1 = null;
        User user2 = null;
        for (int i = 0; i < userCount; i++) {
            if (this.users[i] != null) {
                if (this.users[i].getName().equalsIgnoreCase(name1)) {
                    user1 = this.users[i];
                }
                if (this.users[i].getName().equalsIgnoreCase(name2)) {
                    user2 = this.users[i];
                }
            }
         
            if (user1 != null && user2 != null) {
                break;
            }
        }
    
        if (user1 == null || user2 == null) {
            return false;
        }
    
        return user1.addFollowee(name2);
}
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User current = getUser(name);
    if (current == null) {
        return null; 
    }
    User mostRecommended = null;
    int maxMutual = 0;
    for (int i = 0; i < userCount; i++) {
        User potential = this.users[i];
        if (potential == null || potential == current || current.follows(potential.getName())) {
            continue;
        }
        int mutualCount = potential.countMutual(current);

        if (mutualCount > maxMutual) {
            maxMutual = mutualCount;
            mostRecommended = potential;
        }
    }

    if (mostRecommended != null) {
        return mostRecommended.getName();
    } 
    else {
        return null;
    }  
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null; 
        }
        
        String mostPopular = null;
        int maxFollowers = 0;
    
        for (int i = 0; i < userCount; i++) {
            String currentUserName = this.users[i].getName();
            int followersCount = followeeCount(currentUserName);
    
            if (followersCount > maxFollowers) {
                maxFollowers = followersCount;
                mostPopular = currentUserName;
            }
        }
    
        return mostPopular;
    
    }
    

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;

        for (int i = 0; i < userCount; i++) {
            if (this.users[i] != null) {
                String[] follows = this.users[i].getfFollows();
                if (follows != null) {
                    for (int j = 0; j < follows.length; j++) {
                       
                        if (follows[j] != null && follows[j].equalsIgnoreCase(name)) {
                            count++;
                            break; 
                        }
                    }
                }
            }
        }
    
        return count;
    
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        if (userCount == 0) {
            return "Network:"; 
        }
        
        String result = "Network:\n";
        
        for (int i = 0; i < userCount; i++) {
            result += users[i].getName() + " -> ";
            
            String[] follows = users[i].getfFollows();
            if (users[i].getfCount() > 0) {
                for (int j = 0; j < users[i].getfCount(); j++) {
                    result += follows[j];
                    if (j < users[i].getfCount() - 1) {
                        result += " "; 
                    }
                }
            }
            if(i == userCount-1){
                result += "";
            }
            else{
                result += "\n";
            }
            
        }
        
        return result;
}
}
