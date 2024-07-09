# Autonomous mail delivery trains
This is running in java 17
Assumption:
1. The train can only carry multiples packages only if packages all located at a same node.
2. The train can carry the packages only if the total weight of the packages is less than or equal to the capacity of the train.
3. Djiakstra's algorithm is used to find the shortest path between two nodes.
4. The train will stop moving if there are no packages to deliver and no packages to pick up.
5. The train will pick up the heaviest package first if there are multiple packages at the same node.




