import React, { Component }from 'react';
import PropTypes from 'prop-types';

import { SnackbarProvider } from 'notistack';

import classNames from 'classnames';
import { withStyles } from '@material-ui/core/styles';

/* material-ui */
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import CssBaseline from '@material-ui/core/CssBaseline';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import HomeIcon from '@material-ui/icons/Home';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import StyleIcon from '@material-ui/icons/Style';
import HelpIcon from '@material-ui/icons/Help';
import AssignmentIcon from '@material-ui/icons/Assignment';
import EditIcon from '@material-ui/icons/Edit';
import Grid from '@material-ui/core/Grid';

import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

import HomeComponent from './components/HomeComponent';

import PoolIndexComponent from './components/pools/PoolIndexComponent';
import PoolCreateComponent from './components/pools/PoolCreateComponent';
import PoolShowComponent from './components/pools/PoolShowComponent';
import PoolEditComponent from './components/pools/PoolEditComponent';

import QuestionIndexComponent from './components/questions/QuestionIndexComponent';
import QuestionCreateComponent from './components/questions/QuestionCreateComponent';
import QuestionShowComponent from './components/questions/QuestionShowComponent';
import QuestionEditComponent from './components/questions/QuestionEditComponent';

import QuizIndexComponent from './components/quizzes/QuizIndexComponent';
import QuizCreateComponent from './components/quizzes/QuizCreateComponent';
import QuizShowComponent from './components/quizzes/QuizShowComponent';
import QuizEditComponent from './components/quizzes/QuizEditComponent';
import MyQuizzesComponent from './components/quizzes/MyQuizzesComponent';
import TakeAQuizComponent from './components/takeAQuiz/TakeAQuizComponent';
import TakeAQuizResultComponent from './components/takeAQuiz/TakeAQuizResultComponent';
//colores material-ui
import indigo from '@material-ui/core/colors/indigo';
const drawerWidth = 240;

const styles = theme => ({
  root: {
    display: 'flex',
       backgroundImage: `url(${ '/images/wall.png' })`,
       backgroundRepeat  : 'repeat',
        backgroundSize: 20,
  },
  appBar: {
    backgroundColor:indigo[900],
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    color:'#EA7600',
    marginLeft: 12,
    marginRight: 36,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',

  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing.unit * 7 + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing.unit * 9 + 1,
    },
  },
  toolbar: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
    
  },
});

class App extends Component {
  constructor(props) {
      super(props);

      this.state = {
        open: true,
        selectedIndex: 0
      };
    }

  componentDidMount() {
    this.getCurrentIndexMenu();
  }

  getCurrentIndexMenu() {
    // "http://localhost:3000/pools/1/edit" => ["pools", "1", "edit"]
    var path = window.location.href.split("/").slice(3);
    var currentIndex = 0;

    switch(path[0]) {
      case "pools":     currentIndex = 1; break;
      case "questions": currentIndex = 2; break;
      case "quizzes":   currentIndex = 3; break;
      case "my":        currentIndex = 4; break;
      default:          currentIndex = 0;
    }
    this.setState({ selectedIndex: currentIndex });
  }

  handleListItemClick = (event, index) => {
    this.setState({ selectedIndex: index });
  };

  handleDrawerOpen = () => {
    this.setState({ open: true });
  };

  handleDrawerClose = () => {
    this.setState({ open: false });
  };

  render() {
    const { classes, theme } = this.props;

    return (
      <SnackbarProvider maxSnack={7}>
        <Router>
          <div className={classes.root}>
            <CssBaseline />
            <AppBar
              position="fixed" color='primary'
              className={classNames(classes.appBar, {
                [classes.appBarShift]: this.state.open,
              })}
            >
              <Toolbar disableGutters={!this.state.open}>
                <IconButton
                  color="inherit"
                  aria-label="Open drawer"
                  onClick={this.handleDrawerOpen}
                  className={classNames(classes.menuButton, {
                    [classes.hide]: this.state.open,
                  })}
                >
                  <MenuIcon />
                </IconButton>
               
                <Grid container direction="row" justify="space-between" alignItems="center" spacing={16}>
                  <Grid item xs={6}>
                    <img alt="Quickiz Logo" src={'/images/quickizletras.png'} height="50"/>
                  </Grid>
                  <Grid item xs={6} className="text-right">
                    <img alt="UdeS Logo" src={'/images/udes.png'} height="50"/>
                  </Grid>
                </Grid>                 
              </Toolbar>
            </AppBar>
            <Drawer
              variant="permanent"
              className={classNames(classes.drawer, {
                [classes.drawerOpen]: this.state.open,
                [classes.drawerClose]: !this.state.open,
              })}
              classes={{
                paper: classNames({
                  [classes.drawerOpen]: this.state.open,
                  [classes.drawerClose]: !this.state.open,
                }),
              }}
              open={this.state.open}
            >
              <div className={classes.toolbar}>
                <IconButton onClick={this.handleDrawerClose}>
                  {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
                </IconButton>
              </div>
              <List>
                  <ListItem selected={this.state.selectedIndex === 0} onClick={event => this.handleListItemClick(event, 0)} button component={Link} to="/">
                    <ListItemIcon><HomeIcon /></ListItemIcon>
                    <ListItemText primary="Home" />
                  </ListItem>
              </List>
              <Divider />
              <List>
                  <ListItem selected={this.state.selectedIndex === 1} onClick={event => this.handleListItemClick(event, 1)} button component={Link} to="/pools">
                    <ListItemIcon><StyleIcon/></ListItemIcon>
                    <ListItemText primary="Pozos" />
                  </ListItem>
                  <ListItem selected={this.state.selectedIndex === 2} onClick={event => this.handleListItemClick(event, 2)} button component={Link} to="/questions">
                    <ListItemIcon><HelpIcon/></ListItemIcon>
                    <ListItemText primary="Preguntas" />
                  </ListItem>
                  <ListItem selected={this.state.selectedIndex === 3} onClick={event => this.handleListItemClick(event, 3)} button component={Link} to="/quizzes">
                    <ListItemIcon><AssignmentIcon/></ListItemIcon>
                    <ListItemText primary="Quizzes" />
                  </ListItem>
              </List>
              <Divider />
              <List>
                  <ListItem selected={this.state.selectedIndex === 4} onClick={event => this.handleListItemClick(event, 4)} button component={Link} to="/my/quizzes">
                    <ListItemIcon><EditIcon /></ListItemIcon>
                    <ListItemText primary="Mis Quizzes" />
                  </ListItem>
              </List>
            </Drawer>
            <main className={classes.content}>
              <div className={classes.toolbar} />
              <Switch>
                <Route exact path="/" component={HomeComponent} />

                <Route exact path="/pools" component={PoolIndexComponent} />
                <Route exact path="/pools/create" component={PoolCreateComponent} />
                <Route exact path="/pools/:id" component={PoolShowComponent} />
                <Route exact path="/pools/:id/edit" component={PoolEditComponent} />
                
                <Route exact path="/questions" component={QuestionIndexComponent} />
                <Route exact path="/questions/create" component={QuestionCreateComponent} />
                <Route exact path="/questions/:id" component={QuestionShowComponent} />
                <Route exact path="/questions/:id/edit" component={QuestionEditComponent} />

                <Route exact path="/quizzes" component={QuizIndexComponent} />
                <Route exact path="/quizzes/create" component={QuizCreateComponent} />
                <Route exact path="/quizzes/:id" component={QuizShowComponent} />
                <Route exact path="/quizzes/:id/edit" component={QuizEditComponent} />

                <Route exact path="/my/quizzes" component={MyQuizzesComponent} />
                <Route exact path="/my/quizzes/:id" component={TakeAQuizComponent} />
                <Route exact path="/my/quizzes/:id/result" component={TakeAQuizResultComponent}/>
              </Switch>
            </main>
          </div>
        </Router>
      </SnackbarProvider>
    );
  }
}

App.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(App);